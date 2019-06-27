package com.us.masterpass.merchantapp.data.device;

import android.provider.BaseColumns;

/**
 * The contract used for the db to save the tasks locally.
 */
public final class ItemMapPersistence {

    private ItemMapPersistence() {}

    /**
     * The type Item entry.
     */
    public static abstract class ItemEntry implements BaseColumns {
        /**
         * The constant TABLE_NAME.
         */
        public static final String TABLE_NAME = "items";
        /**
         * The constant COLUMN_NAME_PRODUCT_ID.
         */
        public static final String COLUMN_NAME_PRODUCT_ID = "productId";
        /**
         * The constant COLUMN_NAME_NAME.
         */
        public static final String COLUMN_NAME_NAME = "name";
        /**
         * The constant COLUMN_NAME_PRICE.
         */
        public static final String COLUMN_NAME_PRICE = "price";
        /**
         * The constant COLUMN_NAME_SALE_PRICE.
         */
        public static final String COLUMN_NAME_SALE_PRICE = "salePrice";
        /**
         * The constant COLUMN_NAME_IMAGE.
         */
        public static final String COLUMN_NAME_IMAGE = "image";
        /**
         * The constant COLUMN_NAME_DESCRIPTION.
         */
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        /**
         * The constant COLUMN_NAME_DATE_ADDED.
         */
        public static final String COLUMN_NAME_DATE_ADDED = "dateAdded";
        /**
         * The constant COLUMN_NAME_SELECTED.
         */
        public static final String COLUMN_NAME_SELECTED = "selected";
    }

    /**
     * The type Cart entry.
     */
    public static abstract class CartEntry implements BaseColumns {
        /**
         * The constant TABLE_NAME.
         */
        public static final String TABLE_NAME = "cart";
        /**
         * The constant COLUMN_NAME_PRODUCT_ID.
         */
        public static final String COLUMN_NAME_PRODUCT_ID = "productId";
        /**
         * The constant COLUMN_NAME_TOTAL.
         */
        public static final String COLUMN_NAME_TOTAL = "total";
    }
}
