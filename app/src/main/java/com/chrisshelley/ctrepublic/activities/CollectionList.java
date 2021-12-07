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

public class CollectionList extends AppCompatActivity {
    private ArrayList<CollectionItem> mCollection;
    private DBHandler dbHandler;
    private RecyclerView rvCollectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection_list);

        dbHandler = CTRepublic.getInstance().getDBHandler(this);
        mCollection = dbHandler.getCollection();
        setTitle("Collection List");

        final CollectionListAdapter collectionListAdapter = new CollectionListAdapter(this);
        rvCollectionList = findViewById(R.id.rv_collection_list);
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

        SearchView searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setSubmitButtonEnabled(true);
        searchView.setIconified(false);
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint("Search Keyword");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchTerms) {
                search(searchTerms);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String searchTerms) {
                search(searchTerms);
                return false;
            }
        });
        return true;
    }

    public void search(String searchTerm) {
        ArrayList<CollectionItem> searchResults = dbHandler.search(searchTerm);
        if (searchResults != null) {
            mCollection = searchResults;
            final CollectionListAdapter searchListAdapter = new CollectionListAdapter(this);
            rvCollectionList.setAdapter(searchListAdapter);
        }
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
        navigationIntent.putExtra(CTRepublic.NAVIGATION_OPTION, CTRepublic.NAVIGATE_COLLECTION_LIST);
        startActivity(navigationIntent);
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mItemName;
        private TextView mItemType;
        private TextView mItemSubType;
        private ImageView mFeaturedImage;
        private CollectionItem mCollectionItem;

        public ItemHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_collection_item, parent, false));

            itemView.setOnClickListener(this);
            this.mItemName = itemView.findViewById(R.id.item_name);
            this.mItemType = itemView.findViewById(R.id.item_type);
            this.mItemSubType = itemView.findViewById(R.id.item_subtype);
            this.mFeaturedImage = itemView.findViewById(R.id.item_featured_image);
        }

        public void bind(CollectionItem collectionItem) {
            mCollectionItem = collectionItem;
            mItemName.setText(mCollectionItem.getName());
            mItemType.setText(mCollectionItem.getItemType());
            mItemSubType.setText(mCollectionItem.getItemSubtype());
            mFeaturedImage.setImageURI(mCollectionItem.getFeaturedImageURI());
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