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
    public static final Integer NAVIGATE_COLLECTION_LIST = 0;
    public static final Integer NAVIGATE_REPORTS = 1;
    public static final String NAVIGATION_OPTION = "NAVIGATION_OPTION";
    public static final String SHARED_PREFERENCES = "CTRepublic_PREFERENCES";
    public static final String CREATE_SAMPLE_DATA = "CREATE_SAMPLE_DATA";
    public static final String ITEM_ID = "ITEM_ID";
    public static final String EMPTY_CHOICE = "----------";
    public static final String TYPE_PUTTER_COVER = "Headcover - Putter";
    public static final String TYPE_WOOD_COVER = "Headcover - Wood";
    public static final String TYPE_ACCESSORY = "Accessory";
    public static final String SUBTYPE_BLADE = "Blade";
    public static final String SUBTYPE_MIDMALLET = "Mid Mallet";
    public static final String SUBTYPE_MIDROUND = "Mid Round";
    public static final String SUBTYPE_DRIVER = "Driver";
    public static final String SUBTYPE_FAIRWAY = "Fairway";
    public static final String SUBTYPE_HYBRID = "Hybrid";
    public static final String SUBTYPE_CARTBAG = "Cart Bag";
    public static final String SUBTYPE_STANDBAG = "Stand Bag";
    public static final String SUBTYPE_PIVOTTOOL = "Pivot Tool";
    public static final String SUBTYPE_TOWEL = "Towel";
    public static final Integer PICK_IMAGE = 100;

    /*
    Design and develop a fully functional software application that addresses your identified business problem or organizational need. Include each of the following attributes as they are the minimum required elements for the application:
    DONE: one of the following application types: mobile, web, or stand-alone application
    DONE: code including inheritance, polymorphism, and encapsulation
    DONE: search functionality with multiple row results and displays - Collection List search
    DONE a database component with the functionality to securely add, modify, and delete the data
    DONE: ability to generate reports with multiple columns, multiple rows, date-time stamp, and title
    DONE: exception controls - Use Try Catches
    DONE: validation functionality - Validate form fields
    DONE: industry appropriate security features - Use SQL parameters instead to defend against malicious customer input
    DONE: design elements that make the application scalable - Recycle view
    DONE: a user-friendly, functional GUI - Intuitive UI design
    */

    private static CTRepublic sCTRepublic;
    private DBHandler mDBHandler;

    public static CTRepublic getInstance() {
        if (sCTRepublic == null) {
            sCTRepublic = new CTRepublic();
        }
        return sCTRepublic;
    }

    public static String[] getTypeChoices() {
        String[] choices = new String[]{
                CTRepublic.EMPTY_CHOICE,
                CTRepublic.TYPE_PUTTER_COVER,
                CTRepublic.TYPE_WOOD_COVER,
                CTRepublic.TYPE_ACCESSORY };
        return choices;
    }

    public static String[] getDefaultSubTypeChoices() {
        String[] choices = new String[]{
                CTRepublic.EMPTY_CHOICE };
        return choices;
    }

    public static String[] getAccessorySubTypeChoices() {
        String[] choices = new String[]{
                CTRepublic.EMPTY_CHOICE,
                CTRepublic.SUBTYPE_CARTBAG,
                CTRepublic.SUBTYPE_STANDBAG,
                CTRepublic.SUBTYPE_PIVOTTOOL,
                CTRepublic.SUBTYPE_TOWEL};
        return choices;
    }

    public static String[] getPutterSubTypeChoices() {
        String[] choices = new String[]{
                CTRepublic.EMPTY_CHOICE,
                CTRepublic.SUBTYPE_BLADE,
                CTRepublic.SUBTYPE_MIDMALLET,
                CTRepublic.SUBTYPE_MIDROUND };
        return choices;
    }

    public static String[] getWoodSubTypeChoices() {
        String[] choices = new String[]{
                CTRepublic.EMPTY_CHOICE,
                CTRepublic.SUBTYPE_DRIVER,
                CTRepublic.SUBTYPE_FAIRWAY,
                CTRepublic.SUBTYPE_HYBRID };
        return choices;
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

    public static CollectionItem getCollectionClass(String itemType) {
        if (itemType.equals(CTRepublic.TYPE_PUTTER_COVER)) {
            return new PutterCover();
        } else if (itemType.equals(CTRepublic.TYPE_WOOD_COVER)) {
            return new WoodCover();
        } else {
            return new Accessory();
        }
    }
}
