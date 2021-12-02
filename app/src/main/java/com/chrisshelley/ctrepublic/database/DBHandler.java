package com.chrisshelley.ctrepublic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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
    private static final String DATABASE_NAME = "ctRepublic.db";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void create_sample_data() {
        //TODO: this
    }

    public ArrayList<CollectionItem> getCollection() {
        ArrayList<CollectionItem> collection = new ArrayList<>();
        //TODO: this
        PutterCover pc1 = new PutterCover();
        pc1.setID(1);
        pc1.setName("Putter Cover 1");
        pc1.setNotes("Yo, Notes Go HERE");
        pc1.setPurchasePrice(159.99);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        try {
            pc1.setReleaseDate(formatter.parse("10/09/2019"));
        } catch (ParseException e) {
            //pass
        }
        PutterCover pc2 = new PutterCover();
        pc2.setID(2);
        pc2.setName("Putter Cover 2");
        WoodCover wc1 = new WoodCover();
        wc1.setName("Wood Cover 1");
        wc1.setID(3);
        collection.add(pc1);
        collection.add(pc2);
        collection.add(wc1);
        return collection;
    }

    public CollectionItem getItem(int id) {
        //TODO: this
        //TODO: use safe sql
        //TODO: use try catch around date conversion maybe
        if (id == CTRepublic.NO_DATABASE_ID) {
            return null;
        }
        PutterCover pc1 = new PutterCover();
        pc1.setID(1);
        pc1.setName("Putter Cover 1");
        pc1.setNotes("Yo, Notes Go HERE");
        pc1.setPurchasePrice(159.99);
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        try {
            pc1.setReleaseDate(formatter.parse("10/09/2019"));
        } catch (ParseException e) {
            //pass
        }
        return pc1;
    }

    public void deleteItem(int id) {
        //TODO: this
    }

    public void saveItem(CollectionItem collectionItem) {
        //TODO: use safe sql
    }

}
