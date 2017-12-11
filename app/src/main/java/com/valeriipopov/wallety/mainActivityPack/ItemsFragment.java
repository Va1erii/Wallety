package com.valeriipopov.wallety.mainActivityPack;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valeriipopov.wallety.Item;
import com.valeriipopov.wallety.addActivityPack.AddActivity;
import com.valeriipopov.wallety.R;
import com.valeriipopov.wallety.data.DataBaseContract.*;
import com.valeriipopov.wallety.data.DataBaseDbHelper;

import java.util.ArrayList;
import java.util.List;

import static com.valeriipopov.wallety.addActivityPack.AddActivity.*;
import static com.valeriipopov.wallety.Item.*;

public class ItemsFragment extends Fragment {

    private static final String KEY_TYPE = "TYPE";
    private String mType = TYPE_UNKNOWN;
    private RecyclerView mRecyclerView;
    private MyRecyclerViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private FloatingActionButton mFloatingActionButton;
    private SwipeRefreshLayout mRefreshLayout;
    private List <Item> mItemsList = new ArrayList<>();
    private DataBaseDbHelper mDataBaseDbHelper;


    public static ItemsFragment createItemFragment(String type) {
        ItemsFragment fragment = new ItemsFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_TYPE, type);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getString(KEY_TYPE, TYPE_UNKNOWN);
        if (mType.equals(TYPE_UNKNOWN)) {
            throw new IllegalStateException("Unknown Fragment Type");
        }
        mAdapter = new MyRecyclerViewAdapter();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_items, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mFloatingActionButton = view.findViewById(R.id.fab_add);
        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddActivity.class);
                intent.putExtra(EXTRA_TYPE, mType);
                startActivityForResult(intent, RC_ADD_ITEM);
            }
        });

        mRecyclerView = view.findViewById(R.id.my_recycler_view);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mRefreshLayout = view.findViewById(R.id.refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDataBaseDbHelper.loadItems(mType, mItemsList);
                mRefreshLayout.setRefreshing(false);
            }
        });

        mDataBaseDbHelper = new DataBaseDbHelper(getContext());
        mDataBaseDbHelper.loadItems(mType, mItemsList);
        mAdapter.setItems(mItemsList);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_ADD_ITEM && resultCode == RESULT_OK) {
            Item mItem = (Item) data.getSerializableExtra(RESULT_ITEM);
            mItemsList.add(mItem);
            mDataBaseDbHelper.addNewItem(mItem);
            mAdapter.notifyDataSetChanged();
        }
    }


}
