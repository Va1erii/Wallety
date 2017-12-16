package com.valeriipopov.wallety.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.valeriipopov.wallety.Item;
import com.valeriipopov.wallety.data.DataBaseContract.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

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
        DataBaseAddNewItemTask addNewItemTask = new DataBaseAddNewItemTask();
        addNewItemTask.execute(item);
    }

    public List <Item> loadItems (String type){
        DataBaseLoadItemsTask loadItemsTask = new DataBaseLoadItemsTask();
        loadItemsTask.execute(type);
        try {
            return loadItemsTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int loadTotalValuesForBalance (String type) {
        DataBaseLoadTotalValuesTask loadTotalValuesTask = new DataBaseLoadTotalValuesTask();
        loadTotalValuesTask.execute(type);
        try {
            return loadTotalValuesTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public String getUserPassCode () {
        DataBaseGetUserPassTask getUserPassTask = new DataBaseGetUserPassTask();
        getUserPassTask.execute();
        try {
            return getUserPassTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addNewPasscode(String text) {
        DataBaseAddNewPassTask addNewPassTask = new DataBaseAddNewPassTask();
        addNewPassTask.execute(text);
    }

    public void deleteItem(int id) {
        DataBaseDeleteItemTask dataBaseDeleteItemTask = new DataBaseDeleteItemTask();
        dataBaseDeleteItemTask.execute(id);
    }

    public int dataBaseSize(){
        DataBaseGetSizeTask getSizeTask = new DataBaseGetSizeTask();
        getSizeTask.execute();
        try {
            getSizeTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return 0;
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


    private class DataBaseLoadItemsTask extends AsyncTask <String, Void, List<Item>> {

        @Override
        protected List<Item> doInBackground(String... type) {
            mDataBaseItems = getReadableDatabase();
            List<Item> itemList = new ArrayList<>();
            String [] projection = {
                    ItemsData._ID,
                    ItemsData.COLUMN_NAME_ITEMS,
                    ItemsData.COLUMN_PRICE_ITEMS,
                    ItemsData.COLUMN_TYPE_ITEMS
            };
            String selection = ItemsData.COLUMN_TYPE_ITEMS + "=?";
            String [] selectionArgs = {type[0]};

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
                    itemList.add(new Item(currentName, currentPrice, currentType, currentID));
                }
                return itemList;
            } finally {
                cursor.close();
            }
        }

        @Override
        protected void onPostExecute(List<Item> items) {
            super.onPostExecute(items);
        }
    }

    private class DataBaseAddNewItemTask extends AsyncTask<Item, Void, Void> {

        @Override
        protected Void doInBackground(Item... items) {
            mDataBaseItems = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ItemsData.COLUMN_NAME_ITEMS, items[0].getName());
            values.put(ItemsData.COLUMN_PRICE_ITEMS, items[0].getPrice());
            values.put(ItemsData.COLUMN_TYPE_ITEMS, items[0].getType());

            long newRowID = mDataBaseItems.insert(ItemsData.TABLE_ITEMS, null, values);
            return null;
        }
    }

    private class DataBaseLoadTotalValuesTask extends AsyncTask <String, Void, Integer> {

        @Override
        protected Integer doInBackground(String... type) {
            int mTotalValue = 0;
            mDataBaseItems = getReadableDatabase();
            String [] projection = {
                    ItemsData.COLUMN_PRICE_ITEMS,
                    ItemsData.COLUMN_TYPE_ITEMS
            };

            String selection = ItemsData.COLUMN_TYPE_ITEMS + "=?";
            String [] selectionArgs = {type[0]};

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

        @Override
        protected void onPostExecute(Integer totalValue) {
            super.onPostExecute(totalValue);
        }
    }

    private class DataBaseGetUserPassTask extends AsyncTask <Void, Void, String> {

        @Override
        protected String doInBackground(Void... voids) {
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    private class DataBaseAddNewPassTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... text) {
            mDataBaseItems = getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(ItemsData.COLUMN_PASSCODE_USERS, text[0]);

            long newRowID = mDataBaseItems.insert(ItemsData.TABLE_USERS, null, values);
            return null;
        }
    }

    private class DataBaseDeleteItemTask extends AsyncTask<Integer, Void, Void> {

        @Override
        protected Void doInBackground(Integer... id) {
            mDataBaseItems = getWritableDatabase();
            mDataBaseItems.delete(ItemsData.TABLE_ITEMS,
                    ItemsData._ID + "=?", new String[] {Integer.toString(id[0])});
            return null;
        }
    }

    private class DataBaseGetSizeTask extends AsyncTask <Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            mDataBaseItems = getReadableDatabase();
            String [] projection = {
                    ItemsData._ID,
            };

            Cursor cursor = mDataBaseItems.query(
                    ItemsData.TABLE_ITEMS,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            return cursor.getCount();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }
}
