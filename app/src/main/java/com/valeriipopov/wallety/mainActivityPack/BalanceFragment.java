package com.valeriipopov.wallety.mainActivityPack;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valeriipopov.wallety.Item;
import com.valeriipopov.wallety.R;
import com.valeriipopov.wallety.data.DataBaseContract;
import com.valeriipopov.wallety.data.DataBaseDbHelper;

import java.text.NumberFormat;

public class BalanceFragment extends Fragment {

    private TextView mSumExpence;
    private TextView mSumIncome;
    private TextView mBalance;
    private DataBaseDbHelper mDataBaseDbHelper;
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
        mDataBaseDbHelper = new DataBaseDbHelper(getContext());

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
        mTotalExpense = mDataBaseDbHelper.loadTotalValuesForBalance(Item.TYPE_EXPENSE);
        mTotalIncome = mDataBaseDbHelper.loadTotalValuesForBalance(Item.TYPE_INCOME);
        mBalanceValue = mTotalIncome - mTotalExpense;

        mBalance.setText(NumberFormat.getCurrencyInstance().format(mBalanceValue));
        mSumExpence.setText(NumberFormat.getCurrencyInstance().format(mTotalExpense));
        mSumIncome.setText(NumberFormat.getCurrencyInstance().format(mTotalIncome));
    }


}
