package com.chrisshelley.ctrepublic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.chrisshelley.ctrepublic.R;
import com.chrisshelley.ctrepublic.database.DBHandler;
import com.chrisshelley.ctrepublic.models.CTRepublic;
import com.chrisshelley.ctrepublic.models.CollectionItem;

public class ItemDetails extends AppCompatActivity {
    private DBHandler mDBHandler;
    private CollectionItem mItem;
    private EditText mItemName;
    private EditText mReleaseDate;
    private EditText mPurchasePrice;
    private EditText mNotes;
    private Button mSaveButton;
    private Spinner mItemType;
    private TextView mItemNameErrorMessage;
    private TextView mPurchasePriceErrorMessage;
    private int defaultItemNameErrorMessageHeight;
    private int defaultPurchasePriceErrorMessageHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        mDBHandler = CTRepublic.getInstance().getDBHandler(this);

        setTitle("Item Details");

        mItemName = (EditText) findViewById(R.id.txt_item_name);
        //mItemName.setText(mItem.getName());

        mItemNameErrorMessage = (TextView) findViewById(R.id.txt_item_name_error);
        mItemNameErrorMessage.setTextColor(Color.RED);
        mItemNameErrorMessage.setVisibility(View.INVISIBLE);
        defaultItemNameErrorMessageHeight = mItemNameErrorMessage.getHeight();
        mItemNameErrorMessage.setHeight(0);
        mPurchasePriceErrorMessage = (TextView) findViewById(R.id.txt_purchase_price_error);
        mPurchasePriceErrorMessage.setTextColor(Color.RED);
        mPurchasePriceErrorMessage.setVisibility(View.INVISIBLE);
        defaultPurchasePriceErrorMessageHeight = mPurchasePriceErrorMessage.getHeight();
        mPurchasePriceErrorMessage.setHeight(0);

        mReleaseDate = (EditText) findViewById(R.id.txt_release_date);
        //mReleaseDate.setText(mItem.getReleaseDate().toString());
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
        //mPurchasePrice.setText(mItem.getPurchasePrice().toString());

        mItemType = (Spinner) findViewById(R.id.spinner_item_type);
        //TODO: Set choices

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
                    mItemNameErrorMessage.setHeight(defaultItemNameErrorMessageHeight);
                    //TODO: Display error message
                } else {
                    //TODO: Remove error message
                }
                // price is required
                if (mPurchasePrice.getText().length() == 0) {
                    passedValidation = false;
                    mPurchasePriceErrorMessage.setVisibility(View.VISIBLE);
                    mPurchasePriceErrorMessage.setHeight(defaultPurchasePriceErrorMessageHeight);
                    //TODO: Display error message
                } else {
                    // Remove error message
                }
                if (passedValidation) {
                    mDBHandler.saveItem(mItem);
                }
            }
        });

        mNotes = (EditText) findViewById(R.id.txt_notes);
        //mNotes.setText(mItem.getName());
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
                //TODO: This
                mDBHandler.deleteItem(mItem.getID());
                CTRepublic.navigateTo(this, CollectionList.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}