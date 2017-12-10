package com.valeriipopov.wallety.mainActivityPack;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valeriipopov.wallety.Item;
import com.valeriipopov.wallety.R;
import com.valeriipopov.wallety.data.DataItemsContract;
import com.valeriipopov.wallety.data.DataItemsDbHelper;

import java.text.NumberFormat;

public class BalanceFragment extends Fragment {

    private TextView mSumExpence;
    private TextView mSumIncome;
    private TextView mBalance;
    private DataItemsDbHelper mDataItemsDbHelper;
    private SQLiteDatabase mDatabase;
    private int mTotalExpense;
    private int mTotalIncome;
    private int mBalanceValue;
    private SwipeRefreshLayout mRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataItemsDbHelper = new DataItemsDbHelper(getContext());

        mBalance = view.findViewById(R.id.sum_balance);
        mSumExpence = view.findViewById(R.id.sum_expense);
        mSumIncome = view.findViewById(R.id.sum_income);
        loadItemsBalance();

        mRefreshLayout = view.findViewById(R.id.refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItemsBalance();
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadItemsBalance(){
        mTotalExpense = loadTotalValues(Item.TYPE_EXPENSE);
        mTotalIncome = loadTotalValues(Item.TYPE_INCOME);
        mBalanceValue = mTotalIncome - mTotalExpense;

        mBalance.setText(NumberFormat.getCurrencyInstance().format(mBalanceValue));
        mSumExpence.setText(NumberFormat.getCurrencyInstance().format(mTotalExpense));
        mSumIncome.setText(NumberFormat.getCurrencyInstance().format(mTotalIncome));
    }

    private int loadTotalValues (String type) {
        int mTotalValue = 0;
        mDatabase = mDataItemsDbHelper.getReadableDatabase();
        String [] projection = {
                DataItemsContract.ItemsData.COLUMN_PRICE,
                DataItemsContract.ItemsData.COLUMN_TYPE
        };

        String selection = DataItemsContract.ItemsData.COLUMN_TYPE + "=?";
        String [] selectionArgs = {type};

        Cursor cursor = mDatabase.query(
                DataItemsContract.ItemsData.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        try {
            int priceColumnIndex = cursor.getColumnIndex(DataItemsContract.ItemsData.COLUMN_PRICE);
            while (cursor.moveToNext()) {
                int currentPrice = cursor.getInt(priceColumnIndex);
                mTotalValue +=currentPrice;
            }
        } finally {
            cursor.close();
        }
        return mTotalValue;
    }
}
