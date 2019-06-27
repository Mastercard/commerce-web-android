package com.us.masterpass.merchantapp.data.device;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ItemSQLiteUtil extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "items.db";

    private static final String TEXT_TYPE = " TEXT";

    private static final String BOOLEAN_TYPE = " INTEGER";

    private static final String COMMA_SEP = ",";

    private static final String SQL_CREATE_ITEMS =
            "CREATE TABLE " + ItemMapPersistence.ItemEntry.TABLE_NAME + " (" +
                    ItemMapPersistence.ItemEntry._ID + TEXT_TYPE + " PRIMARY KEY," +
                    ItemMapPersistence.ItemEntry.COLUMN_NAME_PRODUCT_ID + TEXT_TYPE + COMMA_SEP +
                    ItemMapPersistence.ItemEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    ItemMapPersistence.ItemEntry.COLUMN_NAME_PRICE + TEXT_TYPE + COMMA_SEP +
                    ItemMapPersistence.ItemEntry.COLUMN_NAME_SALE_PRICE + TEXT_TYPE + COMMA_SEP +
                    ItemMapPersistence.ItemEntry.COLUMN_NAME_IMAGE + TEXT_TYPE + COMMA_SEP +
                    ItemMapPersistence.ItemEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    ItemMapPersistence.ItemEntry.COLUMN_NAME_DATE_ADDED + TEXT_TYPE + COMMA_SEP +
                    ItemMapPersistence.ItemEntry.COLUMN_NAME_SELECTED + BOOLEAN_TYPE +
            " )";

    public ItemSQLiteUtil(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ITEMS);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
