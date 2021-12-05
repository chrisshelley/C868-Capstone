package com.chrisshelley.ctrepublic.models;

public class WoodCover extends CollectionItem {
    private String mItemType = CTRepublic.TYPE_WOOD_COVER;

    public String[] getSubtypeChoices() {
        return CTRepublic.getWoodSubTypeChoices();
    }

    public String getItemType() { return mItemType; }
}
