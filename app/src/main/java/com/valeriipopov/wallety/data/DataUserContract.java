package com.valeriipopov.wallety.data;

import android.provider.BaseColumns;

public class DataUserContract {

    private DataUserContract(){};


    public static final class UserData implements BaseColumns {
        public static final String TABLE_NAME = "user";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_PASSCODE = "passcode";
    }
}
