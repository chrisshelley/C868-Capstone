package com.chrisshelley.ctrepublic.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;

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
    private Spinner mItemType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        mDBHandler = CTRepublic.getInstance().getDBHandler(this);

        setTitle("Item Details");
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