package com.chrisshelley.ctrepublic.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chrisshelley.ctrepublic.R;
import com.chrisshelley.ctrepublic.database.DBHandler;
import com.chrisshelley.ctrepublic.models.CTRepublic;
import com.chrisshelley.ctrepublic.models.CollectionItem;

import java.util.ArrayList;

public class CollectionList extends AppCompatActivity {
    private ArrayList<CollectionItem> mCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);

        DBHandler dbHandler = CTRepublic.getInstance().getDBHandler(this);
        mCollection = dbHandler.getCollection();
        setTitle("Collection List");

        final CollectionListAdapter collectionListAdapter = new CollectionListAdapter(this);
        RecyclerView rvCollectionList = findViewById(R.id.rv_collection_list);
        rvCollectionList.setAdapter(collectionListAdapter);
        rvCollectionList.setLayoutManager(new LinearLayoutManager(this));
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
        inflater.inflate(R.menu.menu_collection_list, menu);
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
        startActivity(navigationIntent);
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mItemName;
        private CollectionItem mCollectionItem;

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_collection_item, parent, false));

            itemView.setOnClickListener(this);
            this.mItemName = itemView.findViewById(R.id.item_name);
        }

        public void bind(CollectionItem collectionItem) {
            mCollectionItem = collectionItem;
            mItemName.setText(mCollectionItem.getName());
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
            if (mCollection != null) {
                return mCollection.size();
            }
            return 0;
        }
    }
}