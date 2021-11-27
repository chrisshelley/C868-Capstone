package com.chrisshelley.ctrepublic.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.chrisshelley.ctrepublic.models.CollectionItem;

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
        return collection;
    }
}
