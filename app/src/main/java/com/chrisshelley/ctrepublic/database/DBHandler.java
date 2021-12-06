package com.chrisshelley.ctrepublic.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chrisshelley.ctrepublic.R;
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

    private Context mContext;

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        mContext = context;
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
                + DBSchema.CollectionTable.Cols.FEATURED_IMAGE_URI + " TEXT,"
                + DBSchema.CollectionTable.Cols.NOTES + " TEXT)";

        // SECURITY: None of these fields are exposed to user input can can be trusted to run under execSQL
        db.execSQL(create_collection_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         // Nothing to do for now
    }

    public void create_sample_data() {
        // Create 2 putter covers
        PutterCover pc1 = new PutterCover();
        pc1.setName("2021 US Open");
        pc1.setItemSubtype(CTRepublic.SUBTYPE_BLADE);
        pc1.setReleaseDate("06/14/2021");
        pc1.setPurchasePrice(89.99);
        pc1.setNotes("One of my favorites.");
        pc1.setFeaturedImageURI("android.resource://" + mContext.getPackageName() + "/" + R.mipmap.usopen_2021_putter_blade);
        saveItem(pc1);

        PutterCover pc2 = new PutterCover();
        pc2.setName("2017 Tar Heel");
        pc2.setItemSubtype(CTRepublic.SUBTYPE_MIDMALLET);
        pc2.setReleaseDate("08/08/2017");
        pc2.setPurchasePrice(169.00);
        pc2.setNotes("Bought from user Milton Vanderslice");
        pc2.setFeaturedImageURI("android.resource://" + mContext.getPackageName() + "/" + R.mipmap.tarheel_2017_putter_blade);
        saveItem(pc2);

        // Create 1 Wood cover
        WoodCover wc1 = new WoodCover();
        wc1.setName("2016 72 and Sunny");
        wc1.setItemSubtype(CTRepublic.SUBTYPE_FAIRWAY);
        wc1.setReleaseDate("08/23/2016");
        wc1.setPurchasePrice(129.99);
        wc1.setNotes("Traded for my Masters release hybrid");
        wc1.setFeaturedImageURI("android.resource://" + mContext.getPackageName() + "/" + R.mipmap.sunny_2016_wood);
        saveItem(wc1);

        // Create 2 Accessories
        Accessory acc1 = new Accessory();
        acc1.setName("2021 Dancing Pin Flags");
        acc1.setItemSubtype(CTRepublic.SUBTYPE_PIVOTTOOL);
        acc1.setReleaseDate("08/06/2021");
        acc1.setPurchasePrice(65.99);
        acc1.setNotes("Japan Release");
        acc1.setFeaturedImageURI("android.resource://" + mContext.getPackageName() + "/" + R.mipmap.pinflags_2021_pivot);
        saveItem(acc1);

        Accessory acc2 = new Accessory();
        acc2.setName("2019 Cart Bag - Heather Gray");
        acc2.setItemSubtype(CTRepublic.SUBTYPE_CARTBAG);
        acc2.setReleaseDate("06/16/2019");
        acc2.setPurchasePrice(359.99);
        acc2.setNotes("Currently selling for $3200");
        acc2.setFeaturedImageURI("android.resource://" + mContext.getPackageName() + "/" + R.mipmap.cartbag_heather_grey);
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
                collectionItem = CTRepublic.getCollectionClass(itemType);
                collectionItem.setID(cursor.getInt(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ID)));
                collectionItem.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.NAME)));
                collectionItem.setItemSubtype(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ITEM_SUBTYPE)));
                collectionItem.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.RELEASE_DATE)));
                collectionItem.setPurchasePrice(cursor.getDouble(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.PURCHASE_PRICE)));
                collectionItem.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.NOTES)));
                collectionItem.setFeaturedImageURI(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.FEATURED_IMAGE_URI)));
                collection.add(collectionItem);
            } while (cursor.moveToNext());
        }
        db.close();
        return collection;
    }

    public ArrayList<CollectionItem> search(String searchTerm) {
        ArrayList<CollectionItem> collection = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        //SECURITY: Since keywords come from a user input, we need to make sure that we use parameters
        //          and not inline concatenation to avoid malicious sql.
        String searchQuery = "SELECT * FROM " + DBSchema.CollectionTable.NAME + " where ("
                + DBSchema.CollectionTable.Cols.NAME + " like ? OR "
                + DBSchema.CollectionTable.Cols.NOTES + " like ?)";

        Cursor cursor = db.rawQuery(searchQuery, new String[] { "%" + searchTerm + "%", "%" + searchTerm + "%"  });
        if (cursor.moveToFirst()) {
            do {
                CollectionItem collectionItem;
                String itemType = cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ITEM_TYPE));
                collectionItem = CTRepublic.getCollectionClass(itemType);
                collectionItem.setID(cursor.getInt(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ID)));
                collectionItem.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.NAME)));
                collectionItem.setItemSubtype(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ITEM_SUBTYPE)));
                collectionItem.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.RELEASE_DATE)));
                collectionItem.setPurchasePrice(cursor.getDouble(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.PURCHASE_PRICE)));
                collectionItem.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.NOTES)));
                collectionItem.setFeaturedImageURI(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.FEATURED_IMAGE_URI)));
                collection.add(collectionItem);
            } while (cursor.moveToNext());
        }
        db.close();
        return collection;
    }

    public CollectionItem getItem(int id) {
        CollectionItem collectionItem = null;
        if (id == CTRepublic.NO_DATABASE_ID) {
            return collectionItem;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + DBSchema.CollectionTable.NAME + " WHERE id=" + id, null);
        if (cursor.moveToFirst()) {
            do {
                String itemType = cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ITEM_TYPE));
                collectionItem = CTRepublic.getCollectionClass(itemType);
                collectionItem.setID(cursor.getInt(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ID)));
                collectionItem.setName(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.NAME)));
                collectionItem.setItemSubtype(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.ITEM_SUBTYPE)));
                collectionItem.setReleaseDate(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.RELEASE_DATE)));
                collectionItem.setPurchasePrice(cursor.getDouble(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.PURCHASE_PRICE)));
                collectionItem.setNotes(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.NOTES)));
                collectionItem.setFeaturedImageURI(cursor.getString(cursor.getColumnIndexOrThrow(DBSchema.CollectionTable.Cols.FEATURED_IMAGE_URI)));
            } while (cursor.moveToNext());
        }
        db.close();
        return collectionItem;
    }

    public void deleteItem(int id) {
        if (id != CTRepublic.NO_DATABASE_ID) {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(DBSchema.CollectionTable.NAME, "id=?", new String[]{Integer.toString(id)});
            db.close();
        }
    }

    public int saveItem(CollectionItem collectionItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // SECURITY: Parameters are not the same as simple string substitution; they give their values directly to the database without further interpretation:
        values.put(DBSchema.CollectionTable.Cols.NAME, collectionItem.getName());
        values.put(DBSchema.CollectionTable.Cols.ITEM_TYPE, collectionItem.getItemType());
        values.put(DBSchema.CollectionTable.Cols.ITEM_SUBTYPE, collectionItem.getItemSubtype());
        values.put(DBSchema.CollectionTable.Cols.RELEASE_DATE, collectionItem.getReleaseDate());
        values.put(DBSchema.CollectionTable.Cols.PURCHASE_PRICE, collectionItem.getPurchasePrice());
        values.put(DBSchema.CollectionTable.Cols.NOTES, collectionItem.getNotes());
        values.put(DBSchema.CollectionTable.Cols.FEATURED_IMAGE_URI, collectionItem.getFeaturedImageURIString());

        // SECURITY: Database ID's are not user inputted information and can be trusted
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
