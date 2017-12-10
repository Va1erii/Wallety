package com.valeriipopov.wallety.mainActivityPack;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.valeriipopov.wallety.Item;
import com.valeriipopov.wallety.R;
import com.valeriipopov.wallety.authorizationActivityPack.AuthorizationActivity;
import com.valeriipopov.wallety.authorizationActivityPack.NewUserActivity;
import com.valeriipopov.wallety.data.DataItemsContract;
import com.valeriipopov.wallety.data.DataItemsDbHelper;
import com.valeriipopov.wallety.data.DataUserContract;
import com.valeriipopov.wallety.data.DataUserDbHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private MyPagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private DataUserDbHelper mDataUserDbHelper;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar_main_activity);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        mViewPager = findViewById(R.id.pager);
        mTabLayout = findViewById(R.id.my_tab_layout);

        mDataUserDbHelper = new DataUserDbHelper(getApplicationContext());

        if (mPasscode().equals("wrong")) {
            startActivity(new Intent(this, NewUserActivity.class));
        }
        else {
            startActivity(new Intent(this, AuthorizationActivity.class));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mPasscode().equals("wrong")) {
            mPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), getResources());
            mViewPager.setAdapter(mPagerAdapter);
            mTabLayout.setupWithViewPager(mViewPager);
        }
        else {
            startActivity(new Intent(this, NewUserActivity.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.change_user:
                mDatabase.execSQL(DataUserDbHelper.SQL_DELETE_USER_TABLE);
                mDatabase.execSQL(DataUserDbHelper.SQL_CREATE_USER_TABLE);
                startActivity(new Intent(this, NewUserActivity.class));
                return true;
            case R.id.exit:
                MainActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private String mPasscode () {
        mDatabase = mDataUserDbHelper.getReadableDatabase();
        String [] projection = {
                DataUserContract.UserData.COLUMN_PASSCODE,
        };

        Cursor cursor = mDatabase.query(
                DataUserContract.UserData.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );
        try {
            String mPassCode = "wrong";
            int columnIndexPassCode = cursor.getColumnIndex(DataUserContract.UserData.COLUMN_PASSCODE);
            while (cursor.moveToNext()){
                mPassCode = cursor.getString(columnIndexPassCode);
            }
            if (mPassCode.length() != 0) {
                return mPassCode;
            }
            else
                return mPassCode;
        } finally {
            cursor.close();
        }
    }
}
