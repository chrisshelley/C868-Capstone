package com.chrisshelley.ctrepublic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.chrisshelley.ctrepublic.R;
import com.chrisshelley.ctrepublic.database.DBHandler;
import com.chrisshelley.ctrepublic.models.CTRepublic;
import com.chrisshelley.ctrepublic.models.CollectionItem;

import java.util.ArrayList;

public class Reports extends AppCompatActivity {
    private ArrayList<CollectionItem> mCollection;
    private DBHandler dbHandler;
    private RecyclerView rvCollectionList;
    private TextView mTotalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reports);

        dbHandler = CTRepublic.getInstance().getDBHandler(this);
        mCollection = dbHandler.getCollection();
        setTitle("Total Collection Value");

        final CollectionListAdapter collectionListAdapter = new CollectionListAdapter(this);
        rvCollectionList = findViewById(R.id.rv_report_content_list);
        rvCollectionList.setAdapter(collectionListAdapter);
        rvCollectionList.setLayoutManager(new LinearLayoutManager(this));

        mTotalValue = findViewById(R.id.text_total_price);
        mTotalValue.setText("$" + getCollectionValue());
    }

    @Override
    public boolean onSupportNavigateUp() {
        CTRepublic.navigateTo(this, Home.class);
        return true;
    }

    @Override
    public void onBackPressed() {
        CTRepublic.navigateTo(this, Home.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_reports, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_item:
                Intent navigationIntent = new Intent(this, ItemDetails.class);
                navigationIntent.putExtra(CTRepublic.ITEM_ID, CTRepublic.NO_DATABASE_ID);
                startActivity(navigationIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void onItemSelected(CollectionItem collectionItem) {
        Intent navigationIntent = new Intent(this, ItemDetails.class);
        navigationIntent.putExtra(CTRepublic.ITEM_ID, collectionItem.getID());
        navigationIntent.putExtra(CTRepublic.NAVIGATION_OPTION, CTRepublic.NAVIGATE_REPORTS);
        startActivity(navigationIntent);
    }

    private String getCollectionValue() {
        double totalValue = 0.0;
        for ( int i = 0; i < mCollection.size(); i++ ) {
            totalValue += mCollection.get(i).getPurchasePrice();
        }
        return String.format("%.2f", totalValue);
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mItemName;
        private TextView mReleasedDate;
        private TextView mPurchasePrice;
        private CollectionItem mCollectionItem;

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_report_item, parent, false));

            itemView.setOnClickListener(this);
            this.mItemName = itemView.findViewById(R.id.text_item_name);
            this.mReleasedDate = itemView.findViewById(R.id.text_item_released);
            this.mPurchasePrice = itemView.findViewById(R.id.text_item_price);
        }

        public void bind(CollectionItem collectionItem) {
            mCollectionItem = collectionItem;
            mItemName.setText(mCollectionItem.getName());
            mReleasedDate.setText(mCollectionItem.getReleaseDate());
            mPurchasePrice.setText(mCollectionItem.getPurchasePriceDollarized());
        }

        @Override
        public void onClick(View view) { onItemSelected(mCollectionItem); }
    }

    private class CollectionListAdapter extends RecyclerView.Adapter<ItemHolder> {
        private final Context mContext;

        public CollectionListAdapter(Context context) { mContext = context; }

        @Override
        public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(mContext);
            return new ItemHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ItemHolder holder, int position) {
            CollectionItem collectionItem = mCollection.get(position);
            holder.bind(collectionItem);
        }

        @Override
        public int getItemCount() {
            try {
                return mCollection.size();
            } catch (NullPointerException e) {
                return 0;
            }
        }
    }
}