package com.chrisshelley.ctrepublic.database;

public class DBSchema {
    public static final class CollectionTable {
        public static final String NAME = "collection";

        public static final class Cols {
            public static final String ID = "id";
            public static final String NAME = "name";
            public static final String ITEM_TYPE = "item_type";
            public static final String ITEM_SUBTYPE = "item_subtype";
            public static final String RELEASE_DATE = "release_date";
            public static final String PURCHASE_PRICE = "purchase_price";
            public static final String DESCRIPTION = "description";
            public static final String NOTES = "notes";
        }
    }

    public static final class ImagesTable   {
        public static final String NAME = "item_images";

        public static final class Cols {
            public static final String ID = "id";
            public static final String ITEM_ID = "item_id";
            public static final String IMAGE_PATH = "image_path";
        }
    }
}
