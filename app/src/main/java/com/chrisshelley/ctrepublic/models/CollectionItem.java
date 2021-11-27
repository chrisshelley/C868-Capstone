package com.chrisshelley.ctrepublic.models;

import java.util.Date;

public abstract class CollectionItem {
    private String mName;
    private Integer mItemType;
    private Integer mItemSubtype;
    private Date mReleaseDate;
    private Double mPurchasePrice;
    private String mDescription;
    private String mNotes;

    public String getName() { return mName; }

    public void setName(String name) { mName = name; }

    public Integer getItemType() { return mItemType; }

    public void setItemType(Integer coverType) { mItemType = coverType; }

    public Integer getItemSubtype() { return mItemSubtype; }

    public void setItemSubtype(Integer subtype) { mItemSubtype = subtype; }

    public Date getReleaseDate() { return mReleaseDate; }

    public void setReleaseDate(Date releaseDate) { mReleaseDate = releaseDate; }

    public Double getPurchasePrice() { return mPurchasePrice; }

    public void setPurchasePrice(Double purchasePrice) { mPurchasePrice = purchasePrice; }

    public String getDescription() { return mDescription; }

    public void setDescription(String description) { mDescription = description; }

    public String getNotes() { return mNotes; }

    public void setNotes(String notes) { mNotes = notes; }
}
