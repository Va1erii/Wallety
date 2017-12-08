package com.valeriipopov.wallety.mainActivityPack;

import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.valeriipopov.wallety.R;

import static com.valeriipopov.wallety.mainActivityPack.Item.*;

public class MyPagerAdapter extends FragmentPagerAdapter {
    public static final int NUM_ITEMS = 2;
    private Resources mResources;

    public MyPagerAdapter(FragmentManager fm, Resources resources) {
        super(fm);
        mResources = resources;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: {
                return ItemsFragment.createItemFragment(TYPE_EXPENSE);
            }
            case 1: {
                return ItemsFragment.createItemFragment(TYPE_INCOME);
            }
//            case 2: {
//                BalanceFragment tabBalance = new BalanceFragment();
//                return tabBalance;
//            }
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mResources.getString(R.string.tab_expense);
            case 1:
                return mResources.getString(R.string.tab_income);
            case 2:
                return mResources.getString(R.string.tab_balance);
            default:
                return null;
        }
    }
}
