package com.chrisshelley.ctrepublic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.net.URI;

public class ItemDetails extends AppCompatActivity {
    private DBHandler mDBHandler;
    private CollectionItem mItem;
    private EditText mItemName;
    private EditText mReleaseDate;
    private EditText mPurchasePrice;
    private EditText mNotes;
    private Button mNewImageButton;
    private Spinner mItemType;
    private Spinner mItemSubType;
    private TextView mItemNameErrorMessage;
    private TextView mPurchasePriceErrorMessage;
    private TextView mItemTypeErrorMessage;
    private TextView mItemSubTypeErrorMessage;
    private String mCurrentItemType;
    private String mCurrentItemSubType;
    private Integer mItemID;
    private ImageView mFeaturedImage;
    private Uri mFeaturedImageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        mDBHandler = CTRepublic.getInstance().getDBHandler(this);
        mItemID = getIntent().getIntExtra(CTRepublic.ITEM_ID, CTRepublic.NO_DATABASE_ID);
        mItem = mDBHandler.getItem(mItemID);

        if (mItem == null) {
            mCurrentItemType = CTRepublic.EMPTY_CHOICE;
            mCurrentItemSubType = CTRepublic.EMPTY_CHOICE;
        } else {
            mCurrentItemType = mItem.getItemType();
            mCurrentItemSubType = mItem.getItemSubtype();
        }

        setTitle("Item Details");

        mItemName = (EditText) findViewById(R.id.txt_item_name);

        mItemNameErrorMessage = (TextView) findViewById(R.id.txt_item_name_error);
        mItemNameErrorMessage.setTextColor(Color.RED);
        mItemNameErrorMessage.setVisibility(View.INVISIBLE);

        mPurchasePriceErrorMessage = (TextView) findViewById(R.id.txt_purchase_price_error);
        mPurchasePriceErrorMessage.setTextColor(Color.RED);
        mPurchasePriceErrorMessage.setVisibility(View.INVISIBLE);

        mItemTypeErrorMessage = (TextView) findViewById(R.id.txt_type_error);
        mItemTypeErrorMessage.setTextColor(Color.RED);
        mItemTypeErrorMessage.setVisibility(View.INVISIBLE);

        mItemSubTypeErrorMessage = (TextView) findViewById(R.id.txt_subtype_error);
        mItemSubTypeErrorMessage.setTextColor(Color.RED);
        mItemSubTypeErrorMessage.setVisibility(View.INVISIBLE);

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
            if (typeChoices[i].equals(mCurrentItemType)) {
                initialItemTypeSelectionPosition = i;
            }
        }
        mItemType.setSelection(initialItemTypeSelectionPosition, false);
        mItemType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (!mCurrentItemType.equals(typeChoices[position])) {
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
                    mCurrentItemType = typeChoices[position];
                    optionsAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                    mItemSubType.setAdapter(optionsAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mItemSubType = (Spinner) findViewById(R.id.spinner_item_subtype);
        String[] subTypeChoices;
        if (mItem == null) {
            subTypeChoices = CTRepublic.getDefaultSubTypeChoices();
        } else {
            subTypeChoices = mItem.getSubtypeChoices();
        }
        ArrayAdapter<String> optionsSubTypeAdapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, subTypeChoices);
        optionsSubTypeAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mItemSubType.setAdapter(optionsSubTypeAdapter);
        int initialItemSubTypeSelectionPosition = 0;
        for (int i = 0; i < subTypeChoices.length; i++) {
            if (subTypeChoices[i].equals(mCurrentItemSubType)) {
                initialItemSubTypeSelectionPosition = i;
            }
        }
        mItemSubType.setSelection(initialItemSubTypeSelectionPosition, false);
        mItemSubType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mCurrentItemSubType = subTypeChoices[position];
                } catch (ArrayIndexOutOfBoundsException e) {
                    // pass
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mFeaturedImage = (ImageView) findViewById(R.id.image_item_featured);
        //TODO: Set image if one exists

        mNewImageButton = (Button) findViewById(R.id.btn_new_image);
        mNewImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, CTRepublic.PICK_IMAGE);
            }
        });

        mNotes = (EditText) findViewById(R.id.txt_notes);

        if (mItem != null) {
            mPurchasePrice.setText(String.format("%.2f", mItem.getPurchasePrice()));
            mReleaseDate.setText(mItem.getReleaseDate());
            mItemName.setText(mItem.getName());
            mNotes.setText(mItem.getNotes());
            mFeaturedImageURI = mItem.getFeaturedImageURI();
            mFeaturedImage.setImageURI(mFeaturedImageURI);
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
                if (mItem != null) {
                    mDBHandler.deleteItem(mItem.getID());
                }
                CTRepublic.navigateTo(this, CollectionList.class);
                return true;
            case R.id.menu_save_item:
                saveItem();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CTRepublic.PICK_IMAGE){
            mFeaturedImageURI = data.getData();
            mFeaturedImage.setImageURI(mFeaturedImageURI);
        }
    }

    private void saveItem() {
        boolean passedValidation = true;
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
        // type is required
        if (mItemType.getSelectedItem().toString().equals(CTRepublic.EMPTY_CHOICE)){
            passedValidation = false;
            mItemTypeErrorMessage.setVisibility(View.VISIBLE);
        } else {
            mItemTypeErrorMessage.setVisibility(View.INVISIBLE);
        }
        // subtype is required
        if (mItemSubType.getSelectedItem().toString().equals(CTRepublic.EMPTY_CHOICE)){
            passedValidation = false;
            mItemSubTypeErrorMessage.setVisibility(View.VISIBLE);
        } else {
            mItemSubTypeErrorMessage.setVisibility(View.INVISIBLE);
        }

        if (passedValidation) {
            // Because we can change the item type and therefore the class type during
            // selection.  We need to recreate the object and then pass it to be saved.
            mItem = CTRepublic.getCollectionClass(mCurrentItemType);
            mItem.setID(mItemID);
            mItem.setName(mItemName.getText().toString());
            mItem.setNotes(mNotes.getText().toString());
            mItem.setPurchasePrice(mPurchasePrice.getText().toString());
            mItem.setReleaseDate(mReleaseDate.getText().toString());
            mItem.setItemSubtype(mItemSubType.getSelectedItem().toString());
            mItem.setFeaturedImageURI(mFeaturedImageURI);
            mDBHandler.saveItem(mItem);
            String saveMessage = "Item has been saved.";
            Toast toast = Toast.makeText(getApplicationContext(), saveMessage, Toast.LENGTH_LONG);
            toast.show();
        } else {
            String errorText = "Please address the required fields before item can be saved.";
            Toast toast = Toast.makeText(getApplicationContext(), errorText, Toast.LENGTH_LONG);
            toast.show();
        }
    }
}