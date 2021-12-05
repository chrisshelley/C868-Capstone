package com.chrisshelley.ctrepublic.models;

import java.time.LocalDate;
import java.util.Date;

public abstract class CollectionItem {
    private Integer mID;
    private String mName;
    private String mItemSubtype;
    private Date mReleaseDate;
    private Double mPurchasePrice;
    private String mNotes;

    CollectionItem() {
        mID = CTRepublic.NO_DATABASE_ID;
    }

    public abstract String[] getSubtypeChoices();

    public Integer getID() { return mID; }

    public void setID(Integer id) { mID = id; }

    public String getName() { return mName; }

    public void setName(String name) { mName = name; }

    public abstract String getItemType();

    public String getItemSubtype() { return mItemSubtype; }

    public void setItemSubtype(String subtype) { mItemSubtype = subtype; }

    public Date getReleaseDate() { return mReleaseDate; }

    public String getReleaseDateString() {
        //TODO: Convert string to date
        //TODO: use try catch around date conversion maybe
        return "";
    }

    public void setReleaseDate(Date releaseDate) { mReleaseDate = releaseDate; }

    public void setReleaseDate(String releaseDate) {
        //TODO: Convert string to date
        //TODO: use try catch around date conversion maybe
    }

    public Double getPurchasePrice() { return mPurchasePrice; }

    public void setPurchasePrice(Double purchasePrice) { mPurchasePrice = purchasePrice; }

    public String getNotes() { return mNotes; }

    public void setNotes(String notes) { mNotes = notes; }
}
