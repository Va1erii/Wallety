package com.valeriipopov.wallety.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.EditText;

import com.valeriipopov.wallety.Item;
import com.valeriipopov.wallety.data.DataBaseContract.*;

import java.util.List;

public class DataBaseDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DataItems.db";
    public static final int DATABASE_VERSION = 1;
    public SQLiteDatabase mDataBaseItems;

    public DataBaseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ITEMS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ITEMS_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_USERS_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addNewItem(Item item) {
        mDataBaseItems = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ItemsData.COLUMN_NAME_ITEMS, item.getName());
        values.put(ItemsData.COLUMN_PRICE_ITEMS, item.getPrice());
        values.put(ItemsData.COLUMN_TYPE_ITEMS, item.getType());

        long newRowID = mDataBaseItems.insert(ItemsData.TABLE_ITEMS, null, values);

    }

    public void loadItems (String type, List <Item> itemList) {
        mDataBaseItems = getReadableDatabase();
        String [] projection = {
                ItemsData._ID,
                ItemsData.COLUMN_NAME_ITEMS,
                ItemsData.COLUMN_PRICE_ITEMS,
                ItemsData.COLUMN_TYPE_ITEMS
        };

        String selection = ItemsData.COLUMN_TYPE_ITEMS + "=?";
        String [] selectionArgs = {type};

        Cursor cursor = mDataBaseItems.query(
                ItemsData.TABLE_ITEMS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        try {
            int idColumnIndex = cursor.getColumnIndex(ItemsData._ID);
            int nameColumnIndex = cursor.getColumnIndex(ItemsData.COLUMN_NAME_ITEMS);
            int priceColumnIndex = cursor.getColumnIndex(ItemsData.COLUMN_PRICE_ITEMS);
            int typeColumnIndex = cursor.getColumnIndex(ItemsData.COLUMN_TYPE_ITEMS);
            itemList.clear();
            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                int currentPrice = cursor.getInt(priceColumnIndex);
                String currentType = cursor.getString(typeColumnIndex);
                itemList.add(new Item(currentName, currentPrice, currentType));
            }
        } finally {
            cursor.close();
        }
    }

    public int loadTotalValuesForBalance (String type) {
        int mTotalValue = 0;
        mDataBaseItems = getReadableDatabase();
        String [] projection = {
                ItemsData.COLUMN_PRICE_ITEMS,
                ItemsData.COLUMN_TYPE_ITEMS
        };

        String selection = ItemsData.COLUMN_TYPE_ITEMS + "=?";
        String [] selectionArgs = {type};

        Cursor cursor = mDataBaseItems.query(
                ItemsData.TABLE_ITEMS,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        try {
            int priceColumnIndex = cursor.getColumnIndex(ItemsData.COLUMN_PRICE_ITEMS);
            while (cursor.moveToNext()) {
                int currentPrice = cursor.getInt(priceColumnIndex);
                mTotalValue +=currentPrice;
            }
        } finally {
            cursor.close();
        }
        return mTotalValue;
    }

    public String getUserPassCode () {
        mDataBaseItems = getReadableDatabase();
        String [] projection = {
                ItemsData.COLUMN_PASSCODE_USERS,
        };

        Cursor cursor = mDataBaseItems.query(
                ItemsData.TABLE_USERS,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        try {
            String mPassCode = "";
            int columnIndexPassCode = cursor.getColumnIndex(ItemsData.COLUMN_PASSCODE_USERS);
            while (cursor.moveToNext()){
                mPassCode = cursor.getString(columnIndexPassCode);
            }
            return mPassCode;
        } finally {
            cursor.close();
        }
    }

    public void addNewPasscode(EditText editText) {
        mDataBaseItems = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ItemsData.COLUMN_PASSCODE_USERS, editText.getText().toString());

        long newRowID = mDataBaseItems.insert(ItemsData.TABLE_USERS, null, values);

    }

    public static final String SQL_CREATE_ITEMS_TABLE = "CREATE TABLE " + ItemsData.TABLE_ITEMS + " ("
            + ItemsData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ItemsData.COLUMN_NAME_ITEMS + " TEXT NOT NULL,"
            + ItemsData.COLUMN_PRICE_ITEMS + " INTEGER NOT NULL,"
            + ItemsData.COLUMN_TYPE_ITEMS+ " TEXT NOT NULL)";

    public static final String SQL_DELETE_ITEMS_TABLE = "DROP TABLE IF EXISTS " + ItemsData.TABLE_ITEMS;

    public static final String SQL_CREATE_USERS_TABLE = "CREATE TABLE " + ItemsData.TABLE_USERS + " ("
            + ItemsData._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + ItemsData.COLUMN_PASSCODE_USERS + " TEXT NOT NULL)";

    public static final String SQL_DELETE_USERS_TABLE = "DROP TABLE IF EXISTS " + ItemsData.TABLE_USERS;
}
