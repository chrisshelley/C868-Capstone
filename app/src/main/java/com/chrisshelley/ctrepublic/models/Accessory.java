package com.chrisshelley.ctrepublic.models;

public class Accessory  extends CollectionItem {

    public String[] getSubtypeChoices() {
        String[] choices = new String[]{
                CTRepublic.EMPTY_CHOICE,
                CTRepublic.SUBTYPE_CARTBAG,
                CTRepublic.SUBTYPE_STANDBAG,
                CTRepublic.SUBTYPE_PIVOTTOOL,
                CTRepublic.SUBTYPE_TOWEL};
        return choices;
    }
}
