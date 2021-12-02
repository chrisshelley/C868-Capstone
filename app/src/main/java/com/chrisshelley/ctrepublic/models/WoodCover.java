package com.chrisshelley.ctrepublic.models;

public class WoodCover extends CollectionItem {

    public String[] getSubtypeChoices() {
        String[] choices = new String[]{
                CTRepublic.EMPTY_CHOICE,
                CTRepublic.SUBTYPE_DRIVER,
                CTRepublic.SUBTYPE_FAIRWAY,
                CTRepublic.SUBTYPE_HYBRID };
        return choices;
    }
}
