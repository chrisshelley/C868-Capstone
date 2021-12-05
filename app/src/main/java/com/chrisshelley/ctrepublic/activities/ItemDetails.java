package com.chrisshelley.ctrepublic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.chrisshelley.ctrepublic.R;
import com.chrisshelley.ctrepublic.database.DBHandler;
import com.chrisshelley.ctrepublic.models.Accessory;
import com.chrisshelley.ctrepublic.models.CTRepublic;
import com.chrisshelley.ctrepublic.models.CollectionItem;
import com.chrisshelley.ctrepublic.models.PutterCover;
import com.chrisshelley.ctrepublic.models.WoodCover;
import com.google.android.material.snackbar.Snackbar;

public class ItemDetails extends AppCompatActivity {
    private DBHandler mDBHandler;
    private CollectionItem mItem;
    private EditText mItemName;
    private EditText mReleaseDate;
    private EditText mPurchasePrice;
    private EditText mNotes;
    private Button mSaveButton;
    private Spinner mItemType;
    private Spinner mItemSubType;
    private TextView mItemNameErrorMessage;
    private TextView mPurchasePriceErrorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        mDBHandler = CTRepublic.getInstance().getDBHandler(this);
        int itemID = getIntent().getIntExtra(CTRepublic.ITEM_ID, CTRepublic.NO_DATABASE_ID);
        mItem = mDBHandler.getItem(itemID);

        setTitle("Item Details");

        mItemName = (EditText) findViewById(R.id.txt_item_name);

        mItemNameErrorMessage = (TextView) findViewById(R.id.txt_item_name_error);
        mItemNameErrorMessage.setTextColor(Color.RED);
        mItemNameErrorMessage.setVisibility(View.INVISIBLE);

        mPurchasePriceErrorMessage = (TextView) findViewById(R.id.txt_purchase_price_error);
        mPurchasePriceErrorMessage.setTextColor(Color.RED);
        mPurchasePriceErrorMessage.setVisibility(View.INVISIBLE);

        mReleaseDate = (EditText) findViewById(R.id.txt_release_date);
        mReleaseDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    CTRepublic.getDateFromPicker(mReleaseDate, ItemDetails.this);
                }
            }
        });
        mReleaseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CTRepublic.getDateFromPicker(mReleaseDate, ItemDetails.this);
            }
        });

        mPurchasePrice = (EditText) findViewById(R.id.txt_purchase_price);

        mItemType = (Spinner) findViewById(R.id.spinner_item_type);
        String[] typeChoices = CTRepublic.getTypeChoices();
        ArrayAdapter<String> optionsTypeAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, typeChoices);
        optionsTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mItemType.setAdapter(optionsTypeAdapter);
        int initialItemTypeSelectionPosition = 0;
        for (int i = 0; i < typeChoices.length; i++) {
            if (typeChoices[i].equals(mItem.getItemType())) {
                initialItemTypeSelectionPosition = i;
            }
        }
        mItemType.setSelection(initialItemTypeSelectionPosition, false);
        mItemType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mItem.getItemType().equals(typeChoices[position])) {
                    ArrayAdapter<String> optionsAdapter;
                    if (typeChoices[position].equals(CTRepublic.TYPE_PUTTER_COVER)) {
                        optionsAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, CTRepublic.getPutterSubTypeChoices());
                    } else if (typeChoices[position].equals(CTRepublic.TYPE_WOOD_COVER)) {
                        optionsAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, CTRepublic.getWoodSubTypeChoices());
                    } else if (typeChoices[position].equals(CTRepublic.TYPE_ACCESSORY)) {
                        optionsAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, CTRepublic.getAccessorySubTypeChoices());
                    } else {
                        optionsAdapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, CTRepublic.getDefaultSubTypeChoices());
                    }
                    optionsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    mItemSubType.setAdapter(optionsAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mItemSubType = (Spinner) findViewById(R.id.spinner_item_subtype);
        String[] subTypeChoices = mItem.getSubtypeChoices();
        ArrayAdapter<String> optionsSubTypeAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, subTypeChoices);
        optionsSubTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mItemSubType.setAdapter(optionsSubTypeAdapter);
        int initialItemSubTypeSelectionPosition = 0;
        for (int i = 0; i < subTypeChoices.length; i++) {
            if (subTypeChoices[i].equals(mItem.getItemSubtype())) {
                initialItemSubTypeSelectionPosition = i;
            }
        }
        mItemSubType.setSelection(initialItemSubTypeSelectionPosition, false);

        mSaveButton = (Button) findViewById(R.id.btn_save_item);
        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean passedValidation = true;
                // TODO: Validate then save
                // Name is required
                if (mItemName.getText().length() == 0) {
                    passedValidation = false;
                    mItemNameErrorMessage.setVisibility(View.VISIBLE);
                } else {
                    mItemNameErrorMessage.setVisibility(View.INVISIBLE);
                }
                // price is required
                if (mPurchasePrice.getText().length() == 0) {
                    passedValidation = false;
                    mPurchasePriceErrorMessage.setVisibility(View.VISIBLE);
                } else {
                    mPurchasePriceErrorMessage.setVisibility(View.INVISIBLE);
                }
                //TODO: Type and Sub Type is required
                if (passedValidation) {
                    // Because we can change the item type and therefore the class type during
                    // selection.  We need to recreate the object and then pass it to be saved.
                    //TODO: This save - Right now nothing in the object has changed
                    mDBHandler.saveItem(mItem);
                } else {
                    String errorText = "Please address the required fields before item can be saved.";
                    Toast toast = Toast.makeText(getApplicationContext(), errorText, Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });

        mNotes = (EditText) findViewById(R.id.txt_notes);

        if (mItem != null) {
            mPurchasePrice.setText(mItem.getPurchasePrice().toString());
            mReleaseDate.setText(mItem.getReleaseDateString());
            mItemName.setText(mItem.getName());
            mNotes.setText(mItem.getNotes());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        CTRepublic.navigateTo(this, CollectionList.class);
        return true;
    }

    @Override
    public void onBackPressed() {
        CTRepublic.navigateTo(this, CollectionList.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_item_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_delete_item:
                mDBHandler.deleteItem(mItem.getID());
                CTRepublic.navigateTo(this, CollectionList.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}