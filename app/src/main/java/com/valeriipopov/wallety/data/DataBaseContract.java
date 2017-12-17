package com.valeriipopov.wallety.data;

import android.provider.BaseColumns;

/**
 * This class is saving data using SQLite
 * A contract class is a container for constants that define names for URIs, tables, and columns
 * The contract class allows you to use the same constants across all the other classes in the same package
 * This lets you change a column name in one place and have it propagate throughout your code
 */

public class DataBaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.

    private DataBaseContract(){};

    // Inner class that defines the table contents (Column and table's names)
    public static final class ItemsData implements BaseColumns {
        public static final String TABLE_ITEMS = "items";
        public static final String TABLE_USERS = "users";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME_ITEMS = "name";
        public static final String COLUMN_PRICE_ITEMS = "price";
        public static final String COLUMN_TYPE_ITEMS = "type";

        public static final String COLUMN_PASSCODE_USERS = "passcode";
    }
}
