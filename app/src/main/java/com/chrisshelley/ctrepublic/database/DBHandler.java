package com.chrisshelley.ctrepublic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chrisshelley.ctrepublic.models.CollectionItem;
import com.chrisshelley.ctrepublic.models.PutterCover;
import com.chrisshelley.ctrepublic.models.WoodCover;

import java.util.ArrayList;

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
        pc1.setName("Putter Cover 1");
        PutterCover pc2 = new PutterCover();
        pc2.setName("Putter Cover 2");
        WoodCover wc1 = new WoodCover();
        wc1.setName("Wood Cover 1");
        collection.add(pc1);
        collection.add(pc2);
        collection.add(wc1);
        return collection;
    }

    public CollectionItem getItem(int id) {
        //TODO: this
        return new PutterCover();
    }

    public void deleteItem(int id) {
        //TODO: this
    }

}
