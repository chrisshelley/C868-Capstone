package com.chrisshelley.ctrepublic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chrisshelley.ctrepublic.models.Accessory;
import com.chrisshelley.ctrepublic.models.CTRepublic;
import com.chrisshelley.ctrepublic.models.CollectionItem;
import com.chrisshelley.ctrepublic.models.PutterCover;
import com.chrisshelley.ctrepublic.models.WoodCover;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

public class DBHandler extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "CTRepublic.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_collection_table = "CREATE TABLE " + DBSchema.CollectionTable.NAME + " ("
                + DBSchema.CollectionTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBSchema.CollectionTable.Cols.NAME + " TEXT,"
                + DBSchema.CollectionTable.Cols.ITEM_TYPE + " TEXT,"
                + DBSchema.CollectionTable.Cols.ITEM_SUBTYPE + " TEXT,"
                + DBSchema.CollectionTable.Cols.RELEASE_DATE + " TEXT,"
                + DBSchema.CollectionTable.Cols.PURCHASE_PRICE + " REAL,"
                + DBSchema.CollectionTable.Cols.NOTES + " TEXT)";

        String create_images_table = "CREATE TABLE " + DBSchema.ImagesTable.NAME + " ("
                + DBSchema.ImagesTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DBSchema.ImagesTable.Cols.ITEM_ID + " INTEGER,"
                + DBSchema.ImagesTable.Cols.IMAGE_PATH + " TEXT)";

        db.execSQL(create_collection_table);
        db.execSQL(create_images_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         // Nothing to do for now
    }

    public void create_sample_data() {
        // Create 2 putter covers
        PutterCover pc1 = new PutterCover();
        pc1.setName("Putter Cover 1");
        pc1.setItemSubtype(CTRepublic.SUBTYPE_BLADE);
        pc1.setReleaseDate("10/09/2018");
        pc1.setPurchasePrice(89.99);
        pc1.setNotes("One of my favorites.");
        saveItem(pc1);

        PutterCover pc2 = new PutterCover();
        pc2.setName("Putter Cover 2");
        pc2.setItemSubtype(CTRepublic.SUBTYPE_MIDMALLET);
        pc2.setReleaseDate("02/14/2020");
        pc2.setPurchasePrice(169.00);
        pc2.setNotes("Bought from user Milton Vanderslice");
        saveItem(pc2);

        // Create 1 Wood cover
        WoodCover wc1 = new WoodCover();
        wc1.setName("Wood Cover 1");
        wc1.setItemSubtype(CTRepublic.SUBTYPE_FAIRWAY);
        wc1.setReleaseDate("01/01/200");
        wc1.setPurchasePrice(129.99);
        wc1.setNotes("Traded for my Masters release hybrid");
        saveItem(wc1);

        // Create 2 Accessories
        Accessory acc1 = new Accessory();
        acc1.setName("Accessory 1");
        acc1.setItemSubtype(CTRepublic.SUBTYPE_PIVOTTOOL);
        acc1.setReleaseDate("08/15/2011");
        acc1.setPurchasePrice(65.99);
        acc1.setNotes("Japan Release");
        saveItem(acc1);

        Accessory acc2 = new Accessory();
        acc2.setName("Accessory 2");
        acc2.setItemSubtype(CTRepublic.SUBTYPE_STANDBAG);
        acc2.setReleaseDate("06/30/2008");
        acc2.setPurchasePrice(359.99);
        acc2.setNotes("Currently selling for $3200");
        saveItem(acc2);
    }

    public ArrayList<CollectionItem> getCollection() {
        ArrayList<CollectionItem> collection = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBSchema.CollectionTable.NAME, null);
        if (cursor.moveToFirst()) {
            do {
                CollectionItem collectionItem;
                String itemType = cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ITEM_TYPE));
                if (itemType.equals(CTRepublic.TYPE_PUTTER_COVER)) {
                    collectionItem = new PutterCover();
                } else if (itemType.equals(CTRepublic.TYPE_WOOD_COVER)) {
                    collectionItem = new WoodCover();
                } else {
                    collectionItem = new Accessory();
                }
                collectionItem.setID(cursor.getInt(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ID)));
                collectionItem.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.NAME)));
                collectionItem.setItemSubtype(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ITEM_SUBTYPE)));
                collectionItem.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.RELEASE_DATE)));
                collectionItem.setPurchasePrice(cursor.getDouble(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.PURCHASE_PRICE)));
                collectionItem.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.NOTES)));
                collection.add(collectionItem);
            } while (cursor.moveToNext());
        }
        db.close();
        return collection;
    }

    public CollectionItem getItem(int id) {
        //TODO: use safe sql
        CollectionItem collectionItem = null;
        if (id == CTRepublic.NO_DATABASE_ID) {
            return collectionItem;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBSchema.CollectionTable.NAME + " WHERE id=" + id, null);
        if (cursor.moveToFirst()) {
            do {
                String itemType = cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ITEM_TYPE));
                if (itemType.equals(CTRepublic.TYPE_PUTTER_COVER)) {
                    collectionItem = new PutterCover();
                } else if (itemType.equals(CTRepublic.TYPE_WOOD_COVER)) {
                    collectionItem = new WoodCover();
                } else {
                    collectionItem = new Accessory();
                }
                collectionItem.setID(cursor.getInt(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ID)));
                collectionItem.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.NAME)));
                collectionItem.setItemSubtype(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ITEM_SUBTYPE)));
                collectionItem.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.RELEASE_DATE)));
                collectionItem.setPurchasePrice(cursor.getDouble(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.PURCHASE_PRICE)));
                collectionItem.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.NOTES)));
            } while (cursor.moveToNext());
        }
        db.close();
        return collectionItem;
    }

    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DBSchema.CollectionTable.NAME, "id=?", new String[]{Integer.toString(id)});
        db.delete(DBSchema.ImagesTable.NAME, "id=?", new String[]{Integer.toString(id)});
        db.close();
    }

    public int saveItem(CollectionItem collectionItem) {
        //TODO: use safe sql
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DBSchema.CollectionTable.Cols.NAME, collectionItem.getName());
        values.put(DBSchema.CollectionTable.Cols.ITEM_TYPE, collectionItem.getItemType());
        values.put(DBSchema.CollectionTable.Cols.ITEM_SUBTYPE, collectionItem.getItemSubtype());
        values.put(DBSchema.CollectionTable.Cols.RELEASE_DATE, collectionItem.getReleaseDateString());
        values.put(DBSchema.CollectionTable.Cols.PURCHASE_PRICE, collectionItem.getPurchasePrice());
        values.put(DBSchema.CollectionTable.Cols.NOTES, collectionItem.getNotes());
        if (collectionItem.getID() == CTRepublic.NO_DATABASE_ID) {
            long id = db.insert(DBSchema.CollectionTable.NAME, null, values);
            collectionItem.setID((int)id);
        } else {
            db.update(DBSchema.CollectionTable.NAME, values, "id=?", new String[]{Integer.toString(collectionItem.getID())});
        }
        db.close();
        return collectionItem.getID();
    }

}
