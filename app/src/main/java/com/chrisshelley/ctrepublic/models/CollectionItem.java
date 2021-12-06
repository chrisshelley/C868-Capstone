package com.chrisshelley.ctrepublic.models;

import android.net.Uri;

import java.time.LocalDate;
import java.util.Date;

public abstract class CollectionItem {
    private Integer mID;
    private String mName;
    private String mItemSubtype;
    private String mReleaseDate;
    private Double mPurchasePrice;
    private String mNotes;
    private Uri mFeaturedImageURI;

    CollectionItem() {
        mID = CTRepublic.NO_DATABASE_ID;
    }

    public abstract String[] getSubtypeChoices();

    public Integer getID() { return mID; }

    public void setID(Integer id) { mID = id; }

    public String getName() { return mName; }

    public void setName(String name) { mName = name; }

    public void setFeaturedImageURI(String uri) {
        if (uri != null) {
            mFeaturedImageURI = Uri.parse(uri);
        }
    }

    public void setFeaturedImageURI(Uri uri) { mFeaturedImageURI = uri; }

    public String getFeaturedImageURIString() {
        if (mFeaturedImageURI == null) {
            return null;
        } else {
            return mFeaturedImageURI.toString();
        }
    }

    public Uri getFeaturedImageURI() { return mFeaturedImageURI; }

    public abstract String getItemType();

    public String getItemSubtype() { return mItemSubtype; }

    public void setItemSubtype(String subtype) { mItemSubtype = subtype; }

    public String getReleaseDate() { return mReleaseDate; }

    public void setReleaseDate(String releaseDate) { mReleaseDate = releaseDate; }

    public Double getPurchasePrice() { return mPurchasePrice; }

    public void setPurchasePrice(String purchasePrice) {
        if (purchasePrice == null) {
            return;
        } else {
            String scrubbedPurchasePrice = purchasePrice.replaceAll("[^\\d.]+", "");
            try {
                mPurchasePrice = Double.parseDouble(scrubbedPurchasePrice);
            } catch (NullPointerException e) {
                mPurchasePrice = 0.00;
            }
        }
    }

    public void setPurchasePrice(Double purchasePrice) { mPurchasePrice = purchasePrice; }

    public String getNotes() { return mNotes; }

    public void setNotes(String notes) { mNotes = notes; }
}
