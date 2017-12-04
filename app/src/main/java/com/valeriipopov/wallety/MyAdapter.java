package com.valeriipopov.wallety;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by werk on 04/12/2017.
 */

public class MyAdapter extends RecyclerView.Adapter <MyAdapter.ViewHolder> {
    private String[] mDataSet;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(TextView v) {
            super(v);
            mTextView = v;
        }
    }

    public MyAdapter (String [] dataSet) {
        mDataSet = dataSet;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        return null;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
