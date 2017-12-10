package com.valeriipopov.wallety.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.valeriipopov.wallety.data.DataItemsContract.*;

public class DataItemsDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DataItems.db";
    public static final int DATABASE_VERSION = 1;

    public DataItemsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ITEMS_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static final String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE " + ItemsData.TABLE_NAME + " ("
            + ItemsData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ItemsData.COLUMN_NAME + " TEXT NOT NULL,"
            + ItemsData.COLUMN_PRICE + " INTEGER NOT NULL,"
            + ItemsData.COLUMN_TYPE+ " TEXT NOT NULL)";

    public static final String SQL_DELETE_ITEMS_TABLE = "DROP TABLE IF EXISTS " + ItemsData.TABLE_NAME;
}
