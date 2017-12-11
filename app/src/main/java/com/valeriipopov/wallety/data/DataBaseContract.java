package com.valeriipopov.wallety.data;

import android.provider.BaseColumns;

public class DataBaseContract {

    private DataBaseContract(){};


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
