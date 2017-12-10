package com.valeriipopov.wallety.data;

import android.provider.BaseColumns;

public class DataItemsContract {

    private DataItemsContract(){};


    public static final class ItemsData implements BaseColumns {
        public static final String TABLE_NAME = "items";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_TYPE = "type";

        public static final int TYPE_EXPENSE = -1;
        public static final int TYPE_INCOME = 1;

    }
}
