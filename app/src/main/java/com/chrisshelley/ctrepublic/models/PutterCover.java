package com.chrisshelley.ctrepublic.models;

public class PutterCover extends CollectionItem {

    public String[] getSubtypeChoices() {
        String[] choices = new String[]{
                CTRepublic.EMPTY_CHOICE,
                CTRepublic.SUBTYPE_BLADE,
                CTRepublic.SUBTYPE_MIDMALLET,
                CTRepublic.SUBTYPE_MIDROUND };
        return choices;
    }
}
