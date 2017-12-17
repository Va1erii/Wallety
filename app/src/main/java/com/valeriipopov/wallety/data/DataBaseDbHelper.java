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

/**
 * DataBaseDbHelper class extends the SQLiteOpenHelper class. We use this class to obtain
 * references to our database. The system performs the potentially long-running operations
 * of creating and updating the database only when needed and not during app startup.
 * So I use AsyncTask fot call these methods getWritableDatabase() and getReadableDatabase().
 */

public class DataBaseDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "DataItems.db";
    public static final int DATABASE_VERSION = 1;
    public SQLiteDatabase mDataBaseItems;
    // Here our database's name and version

    public DataBaseDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // Constructor for creating the database
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ITEMS_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_USERS_TABLE);
        // We create two tables, first for our items and second for user password
        // The statement for create database is bottom of this class
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ITEMS_TABLE);
        sqLiteDatabase.execSQL(SQL_DELETE_USERS_TABLE);
        onCreate(sqLiteDatabase);
        // If we update database we should delete old database and create new
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public void addNewItem(Item item) {
        DataBaseAddNewItemTask addNewItemTask = new DataBaseAddNewItemTask();
        addNewItemTask.execute(item);
        // Create task addNewItem and execute task
        // This task add new item from AddActivity class
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
        // Create task loadItems and execute task
        // This task load list items from database, we put argument type's item (expense or income) in method
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
        // Create task loadTotalValuesForBalance and execute task
        // This task load total value of expense or income, we put argument type's item (expense or income) in method
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
        // Create task getUserPassCode and execute task
        // This task get user's password from database
    }

    public void addNewPasscode(String text) {
        DataBaseAddNewPassTask addNewPassTask = new DataBaseAddNewPassTask();
        addNewPassTask.execute(text);
        // Create task addNewPasscode and execute task
        // This task add new password into table users by our database
    }

    public void deleteItem(int id) {
        DataBaseDeleteItemTask dataBaseDeleteItemTask = new DataBaseDeleteItemTask();
        dataBaseDeleteItemTask.execute(id);
        // Create task deleteItem and execute task
        // This task delete item from database. We put in method item's id which we want to delete
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
        // Create task dataBaseSize and execute task
        // This task get database size. This method I used for add item's id in new item in AddActivity class
    }

    /**
     * These are typical statements that create and delete a table
     */

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

    /**
     * These are my AsyncTasks which I used to connect with database
     */

    private class DataBaseLoadItemsTask extends AsyncTask <String, Void, List<Item>> {
        /**
         * This task get items from database using item's type
         */

        @Override
        protected List<Item> doInBackground(String... type) {
            mDataBaseItems = getReadableDatabase();
            // Gets the data repository in read mode
            List<Item> itemList = new ArrayList<>();
            String [] projection = {
                    ItemsData._ID,
                    ItemsData.COLUMN_NAME_ITEMS,
                    ItemsData.COLUMN_PRICE_ITEMS,
                    ItemsData.COLUMN_TYPE_ITEMS
            };
            // Projection is the list Column in the database. We get values from this column
            String selection = ItemsData.COLUMN_TYPE_ITEMS + "=?";
            // Selection is filter, we get items from database with using type (we get expense items or income items)
            String [] selectionArgs = {type[0]};
            // We put our argument item's type in selectionArgs, expense or income

            Cursor cursor = mDataBaseItems.query(
                    ItemsData.TABLE_ITEMS,         // The table to query
                    projection,                    // The columns to return
                    selection,                     // The columns for the WHERE clause
                    selectionArgs,                 // The values for the WHERE clause
                    null,                 // don't group the rows
                    null,                  // don't filter by row groups
                    null                  // The sort order
            );
            // Cursor helps us to get our values from the database
            try {
                int idColumnIndex = cursor.getColumnIndex(ItemsData._ID);
                int nameColumnIndex = cursor.getColumnIndex(ItemsData.COLUMN_NAME_ITEMS);
                int priceColumnIndex = cursor.getColumnIndex(ItemsData.COLUMN_PRICE_ITEMS);
                int typeColumnIndex = cursor.getColumnIndex(ItemsData.COLUMN_TYPE_ITEMS);
                itemList.clear();
                // Get column index from database
                // We clear our list item's and then we put items from database
                while (cursor.moveToNext()) {
                    int currentID = cursor.getInt(idColumnIndex);
                    String currentName = cursor.getString(nameColumnIndex);
                    int currentPrice = cursor.getInt(priceColumnIndex);
                    String currentType = cursor.getString(typeColumnIndex);
                    itemList.add(new Item(currentName, currentPrice, currentType, currentID));
                    // We move cursor into the database step by step, while cursor reach the table's end
                    // And put new item into itemList using these values
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
        /**
         * This task put item into database using a new item from AddActivity class
         */

        @Override
        protected Void doInBackground(Item... items) {
            mDataBaseItems = getWritableDatabase();
            // Gets the data repository in write mode
            ContentValues values = new ContentValues();
            values.put(ItemsData.COLUMN_NAME_ITEMS, items[0].getName());
            values.put(ItemsData.COLUMN_PRICE_ITEMS, items[0].getPrice());
            values.put(ItemsData.COLUMN_TYPE_ITEMS, items[0].getType());
            // Create a new map of values, where column names are the keys
            // We put in argument our new item ftom AddActivity
            long newRowID = mDataBaseItems.insert(ItemsData.TABLE_ITEMS, null, values);
            // Insert the new row, returning the primary key value of the new row
            return null;
        }
    }

    private class DataBaseLoadTotalValuesTask extends AsyncTask <String, Void, Integer> {
        /**
         * This task load total value from database using item's type. Total expense or income
         */

        @Override
        protected Integer doInBackground(String... type) {
            int mTotalValue = 0;
            mDataBaseItems = getReadableDatabase();
            // Gets the data repository in read mode
            String [] projection = {
                    ItemsData.COLUMN_PRICE_ITEMS,
                    ItemsData.COLUMN_TYPE_ITEMS
            };
            // Projection is the list Column in the database. We get values from this column
            String selection = ItemsData.COLUMN_TYPE_ITEMS + "=?";
            String [] selectionArgs = {type[0]};
            // Selection is filter, we get item's price from database with using type
            Cursor cursor = mDataBaseItems.query(
                    ItemsData.TABLE_ITEMS,         // The table to query
                    projection,                    // The columns to return
                    selection,                     // The columns for the WHERE clause
                    selectionArgs,                 // The values for the WHERE clause
                    null,                 // don't group the rows
                    null,                  // don't filter by row groups
                    null                  // The sort order
            );
            // Cursor helps us to get our values from the database
            try {
                int priceColumnIndex = cursor.getColumnIndex(ItemsData.COLUMN_PRICE_ITEMS);
                while (cursor.moveToNext()) {
                    int currentPrice = cursor.getInt(priceColumnIndex);
                    mTotalValue +=currentPrice;
                }
                // We move cursor into the database step by step, while cursor reach the table's end
                // And consider the total item's price
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
        /**
         * This task get user's password from database
         */

        @Override
        protected String doInBackground(Void... voids) {
            mDataBaseItems = getReadableDatabase();
            // Gets the data repository in read mode
            String [] projection = {
                    ItemsData.COLUMN_PASSCODE_USERS,
            };
            // Projection is the list Column in the database. We get values from this column
            Cursor cursor = mDataBaseItems.query(
                    ItemsData.TABLE_USERS,         // The table to query
                    projection,                    // The columns to return
                    null,                 // The columns for the WHERE clause
                    null,             // The values for the WHERE clause
                    null,                 // don't group the rows
                    null,                  // don't filter by row groups
                    null                  // The sort order
            );
            try {
                String mPassCode = "";
                int columnIndexPassCode = cursor.getColumnIndex(ItemsData.COLUMN_PASSCODE_USERS);
                while (cursor.moveToNext()){
                    mPassCode = cursor.getString(columnIndexPassCode);
                }
                return mPassCode;
                // We get password from database, if password is null we return empty password
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
        /**
         * This task put a new user's password into database
         */

        @Override
        protected Void doInBackground(String... text) {
            mDataBaseItems = getWritableDatabase();
            // Gets the data repository in write mode
            ContentValues values = new ContentValues();
            values.put(ItemsData.COLUMN_PASSCODE_USERS, text[0]);
            // Create a new map of values, where column names are the keys
            // We put in argument our new password from NewUserActivity
            long newRowID = mDataBaseItems.insert(ItemsData.TABLE_USERS, null, values);
            // Insert the new row, returning the primary key value of the new row
            return null;
        }
    }

    private class DataBaseDeleteItemTask extends AsyncTask<Integer, Void, Void> {
        /**
         * This task delete an item from database using item's id
         */

        @Override
        protected Void doInBackground(Integer... id) {
            mDataBaseItems = getWritableDatabase();
            // Gets the data repository in write mode
            mDataBaseItems.delete(ItemsData.TABLE_ITEMS,
                    ItemsData._ID + "=?", new String[] {Integer.toString(id[0])});
            // We delete item from database using item's id
            return null;
        }
    }

    private class DataBaseGetSizeTask extends AsyncTask <Void, Void, Integer> {
        /**
         * This task get count of our items from database. I used this task for
         * to add new item's id
         */

        @Override
        protected Integer doInBackground(Void... voids) {
            mDataBaseItems = getReadableDatabase();
            // Gets the data repository in read mode
            String [] projection = {
                    ItemsData._ID,
            };
            // Projection is the list Column in the database. We get values from this column
            Cursor cursor = mDataBaseItems.query(
                    ItemsData.TABLE_ITEMS,         // The table to query
                    projection,                    // The columns to return
                    null,                 // The columns for the WHERE clause
                    null,             // The values for the WHERE clause
                    null,                 // don't group the rows
                    null,                  // don't filter by row groups
                    null                  // The sort order
            );
            return cursor.getCount();
            // We get cursor's count, it is how many item we have in database
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
        }
    }
}
