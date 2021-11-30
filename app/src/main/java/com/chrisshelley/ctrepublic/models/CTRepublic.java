package com.chrisshelley.ctrepublic.models;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.chrisshelley.ctrepublic.database.DBHandler;

import java.util.Calendar;

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

    /*
    Design and develop a fully functional software application that addresses your identified business problem or organizational need. Include each of the following attributes as they are the minimum required elements for the application:
    DONE: one of the following application types: mobile, web, or stand-alone application
    DONE: code including inheritance, polymorphism, and encapsulation
    TODO: search functionality with multiple row results and displays - Custom Search
    DONE a database component with the functionality to securely add, modify, and delete the data
    TODO: ability to generate reports with multiple columns, multiple rows, date-time stamp, and title
    TODO: exception controls - Use Try Catches
    TODO: validation functionality - Validate form fields
    TODO: industry appropriate security features - SAFE SQL
    DONE: design elements that make the application scalable
    DONE: a user-friendly, functional GUI
    */

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

    public static void getDateFromPicker(EditText control, Context context) {
        final Calendar cal = Calendar.getInstance();
        int currentYear = cal.get(Calendar.YEAR);
        int currentMonth = cal.get(Calendar.MONTH);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String selectedDate = (month + 1) + "/" + dayOfMonth + "/" + year;
                control.setText(selectedDate);
            }
        }, currentYear, currentMonth, currentDay);
        pickerDialog.show();
    }
}
