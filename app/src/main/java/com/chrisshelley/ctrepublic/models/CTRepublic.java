package com.chrisshelley.ctrepublic.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatActivity;

import com.chrisshelley.ctrepublic.database.DBHandler;

public class CTRepublic {
    public static final Integer NO_DATABASE_ID = 0;
    public static final Integer NEED_SAMPLE_DATA = 0;
    public static final Integer SAMPLE_DATA_APPLIED = 1;
    public static final String SHARED_PREFERENCES = "CTRepublic_PREFERENCES";
    public static final String CREATE_SAMPLE_DATA = "CREATE_SAMPLE_DATA";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String TYPE_PUTTER_COVER = "Headcover - Putter";
    public static final String TYPE_WOOD_COVER = "Headcover - Wood";
    public static final String TYPE_ACCESSORY = "Accessory";

    private static CTRepublic sCTRepublic;
    private DBHandler mDBHandler;

    public static CTRepublic getInstance() {
        if (sCTRepublic == null) {
            sCTRepublic = new CTRepublic();
        }
        return sCTRepublic;
    }

    public DBHandler getDBHandler(Context context) {
        if (mDBHandler == null) {
            mDBHandler = new DBHandler(context);

            SharedPreferences sp = context.getSharedPreferences(CTRepublic.SHARED_PREFERENCES, Activity.MODE_PRIVATE);
            int needSampleData = sp.getInt(CTRepublic.CREATE_SAMPLE_DATA, CTRepublic.NEED_SAMPLE_DATA);

            if (needSampleData == CTRepublic.NEED_SAMPLE_DATA) {
                mDBHandler.create_sample_data();
                SharedPreferences.Editor editor = sp.edit();
                editor.putInt(CTRepublic.CREATE_SAMPLE_DATA, CTRepublic.SAMPLE_DATA_APPLIED);
                editor.commit();
            }
        }
        return mDBHandler;
    }

    public static void navigateTo(Context context, Class c) {
        Intent navigationIntent = new Intent(context, c);
        context.startActivity(navigationIntent);
    }
}
