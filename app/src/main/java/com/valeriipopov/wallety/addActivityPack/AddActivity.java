package com.valeriipopov.wallety.addActivityPack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.valeriipopov.wallety.R;
import com.valeriipopov.wallety.Item;
import com.valeriipopov.wallety.data.DataBaseDbHelper;

/**
 * AddActivity class add a new item into our SQLite database
 */

public class AddActivity extends AppCompatActivity {
    public static final String EXTRA_TYPE = "extra_type";
    public static final int RC_ADD_ITEM = 333;
    public static final String RESULT_ITEM = "result_item";

    private Toolbar mToolbar;
    private EditText mItemName;
    private EditText mItemPrice;
    private ImageButton mAddButton;
    private String mType;
    private Item mItem;
    private DataBaseDbHelper mDataBaseDbHelper;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mDataBaseDbHelper = new DataBaseDbHelper(getApplicationContext());
        // create DataBaseHelper, more information about this class in DataBaseDbHelper.class

        mToolbar = findViewById(R.id.toolbar_add_activity);
        mToolbar.setTitle("Add item");
        // set new title in toolbar
        setSupportActionBar(mToolbar);

        mType = getIntent().getStringExtra(EXTRA_TYPE);
        // get item's type from intent, where we click add button (Expense fragment or income fragment)
        mItemName = findViewById(R.id.item_name_edit_text);
        mItemPrice = findViewById(R.id.item_price_edit_text);

        mAddButton = findViewById(R.id.add_button);
        mAddButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isEmpty(mItemName) || isEmpty(mItemPrice)) {
                    Toast.makeText(AddActivity.this, R.string.toast_isEmpty, Toast.LENGTH_SHORT).show();
                    // if our TextViews mItemPrice or mItemName is empty, we show Toast about it
                }
                else {
                    addItem();
                    // more information about this method is bellow
                    finish();
                    // finish this activity and return new Item (Result)
                }
            }
        });

    }

    private void addItem() {
        mItem = new Item(mItemName.getText().toString(),
                Integer.parseInt(mItemPrice.getText().toString()),
                mType,
                mDataBaseDbHelper.dataBaseSize()+1);
        // we create a new item to use our name and price from TextViews (mItemName, mItemPrice)
        // and add new item's id for SQL database, we get database size and when we create new item we increase id++
        Intent intent = new Intent();
        intent.putExtra(RESULT_ITEM, mItem);
        setResult(RESULT_OK, intent);
        // put our new item to intent and return item to our Fragment which there we click method 'add new item'
    }

    private boolean isEmpty (EditText editText) {
        if (editText.getText().length() > 0)
            return false;
        else
            return true;
        // return true if EditText is empty, or false if EditText is not empty
    }

}
