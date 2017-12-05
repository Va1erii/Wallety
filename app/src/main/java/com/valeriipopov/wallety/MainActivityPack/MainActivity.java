package com.valeriipopov.wallety.MainActivityPack;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.valeriipopov.wallety.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Toolbar mToolbar;

    private List<Item> mItems = Collections.EMPTY_LIST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = findViewById(R.id.toolbar_main_activity);
        setSupportActionBar(mToolbar);

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter(mItems);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setItems(createItemList(mItems));


    }

    public List <Item> createItemList(List <Item> items) {
        items = new ArrayList<>();
        items.add(new Item("Milk", 10.00d, MyRecyclerViewAdapter.TYPE_EXPENSE));
        items.add(new Item("Water", 5.00d, MyRecyclerViewAdapter.TYPE_EXPENSE));
        items.add(new Item("Juice", 15.00d, MyRecyclerViewAdapter.TYPE_EXPENSE));
        items.add(new Item("Butter", 6.00d, MyRecyclerViewAdapter.TYPE_EXPENSE));
        items.add(new Item("Salary", 10000.00d, MyRecyclerViewAdapter.TYPE_INCOME));
        items.add(new Item("Rent", 1500.00d, MyRecyclerViewAdapter.TYPE_EXPENSE));
        items.add(new Item("Phone", 200.00d, MyRecyclerViewAdapter.TYPE_EXPENSE));
        items.add(new Item("Cinema", 34.00d, MyRecyclerViewAdapter.TYPE_EXPENSE));
        return items;
    }
}
