package com.valeriipopov.wallety.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.valeriipopov.wallety.data.DataUserContract.*;

public class DataUserDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DataUser.db";
    public static final int DATABASE_VERSION = 1;

    public DataUserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_USER_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + UserData.TABLE_NAME + " ("
            + UserData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + UserData.COLUMN_PASSCODE + " TEXT NOT NULL)";

    public static final String SQL_DELETE_USER_TABLE = "DROP TABLE IF EXISTS " + UserData.TABLE_NAME;
}
