package com.valeriipopov.wallety.mainActivityPack;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.valeriipopov.wallety.R;
import com.valeriipopov.wallety.authorizationActivityPack.AuthorizationActivity;
import com.valeriipopov.wallety.authorizationActivityPack.NewUserActivity;
import com.valeriipopov.wallety.data.DataBaseDbHelper;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private MyPagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;
    private DataBaseDbHelper mDataBaseDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar_main_activity);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        mViewPager = findViewById(R.id.pager);
        mTabLayout = findViewById(R.id.my_tab_layout);

        mDataBaseDbHelper = new DataBaseDbHelper(getApplicationContext());
        if (mDataBaseDbHelper.getUserPassCode().equals("")) {
            startActivity(new Intent(this, NewUserActivity.class));
        }
        else {
            startActivity(new Intent(this, AuthorizationActivity.class));
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mDataBaseDbHelper.getUserPassCode().equals("")) {
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
                mDataBaseDbHelper.mDataBaseItems.execSQL(mDataBaseDbHelper.SQL_DELETE_USERS_TABLE);
                mDataBaseDbHelper.mDataBaseItems.execSQL(mDataBaseDbHelper.SQL_CREATE_USERS_TABLE);
                startActivity(new Intent(this, NewUserActivity.class));
                return true;
            case R.id.exit:
                MainActivity.this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
