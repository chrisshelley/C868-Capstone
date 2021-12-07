package com.chrisshelley.ctrepublic.models;

public class Accessory extends CollectionItem {
    private String mItemType = CTRepublic.TYPE_ACCESSORY;

    public String[] getSubtypeChoices() {
        return CTRepublic.getAccessorySubTypeChoices();
    }

    public String getItemType() { return mItemType; }
}
