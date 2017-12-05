package com.valeriipopov.wallety.MainActivityPack;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valeriipopov.wallety.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by werk on 04/12/2017.
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter <MyRecyclerViewAdapter.ItemsViewHolder> {
    private List<Item> mItems = Collections.EMPTY_LIST;
    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_INCOME = "income";

    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemNameTextView;
        private TextView mItemPriceTextView;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            mItemNameTextView = itemView.findViewById(R.id.item_name_textview);
            mItemPriceTextView = itemView.findViewById(R.id.item_price_textview);
        }
    }

    public MyRecyclerViewAdapter(List<Item> dataSet) {
        mItems = dataSet;
    }

    public void setItems(List <Item> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public MyRecyclerViewAdapter.ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view, parent, false);
        ItemsViewHolder ivh = new ItemsViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(MyRecyclerViewAdapter.ItemsViewHolder holder, int position) {

        holder.mItemNameTextView.setText(mItems.get(position).getName());
        holder.mItemPriceTextView.setText(Double.toString(mItems.get(position).getPrice()) + " $");
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
