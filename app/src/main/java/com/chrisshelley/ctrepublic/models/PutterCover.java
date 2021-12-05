package com.chrisshelley.ctrepublic.models;

public class PutterCover extends CollectionItem {
    private String mItemType = CTRepublic.TYPE_PUTTER_COVER;

    public String[] getSubtypeChoices() {
        return CTRepublic.getPutterSubTypeChoices();
    }

    public String getItemType() { return mItemType; }
}
