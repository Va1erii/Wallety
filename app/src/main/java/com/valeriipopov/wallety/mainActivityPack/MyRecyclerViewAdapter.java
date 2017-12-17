package com.valeriipopov.wallety.mainActivityPack;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.valeriipopov.wallety.Item;
import com.valeriipopov.wallety.R;
import com.valeriipopov.wallety.data.DataBaseDbHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * MyRecyclerViewAdapter fill our RecyclerView in Fragment. This class show our item's cards
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter <MyRecyclerViewAdapter.ItemsViewHolder> {
    private List<Item> mItems = Collections.EMPTY_LIST;
    private List<Item> mSelectedItems = new ArrayList<>();
    private ActionMode mActionMode = null;
    private Resources mResources;
    private List <View> mViewsIsSelected = new ArrayList<>();
    private DataBaseDbHelper mDataBaseDbHelper;
    private AlertDialog mAlertDialog;
    private Context mContext;


    public static class ItemsViewHolder extends RecyclerView.ViewHolder {
        private TextView mItemNameTextView;
        private TextView mItemPriceTextView;
        private CardView mCardView;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            mCardView = itemView.findViewById(R.id.card_view);
            mItemNameTextView = itemView.findViewById(R.id.item_name_textview);
            mItemPriceTextView = itemView.findViewById(R.id.item_price_textview);
        }
    }

    public void setItems(List <Item> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public MyRecyclerViewAdapter.ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_card_view, parent, false);
        ItemsViewHolder ivh = new ItemsViewHolder(v);
        mDataBaseDbHelper = new DataBaseDbHelper(parent.getContext());
        mResources = parent.getResources();
        mContext = parent.getContext();
        return ivh;
    }

    @Override
    public void onBindViewHolder(final MyRecyclerViewAdapter.ItemsViewHolder holder, final int position) {
        holder.mItemNameTextView.setText(mItems.get(position).getName());
        holder.mItemPriceTextView.setText(NumberFormat.getCurrencyInstance().format(mItems.get(position).getPrice()));
        holder.mCardView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (mActionMode != null) {
                    return false;
                }
                else {
                    mActionMode = ((AppCompatActivity)view.getContext()).startSupportActionMode(mActionModeCallback);
                    view.setSelected(true);
                    mSelectedItems.add(mItems.get(position));
                    holder.mCardView.setCardBackgroundColor(mResources.getColor(R.color.colorSelected));
                    mViewsIsSelected.add(view);
                    mActionMode.setTitle(mSelectedItems.size() + " selected");
                    return true;
                    // If ActionMode doesn't exist we create it. And we change card's background to select color
                    // We put the item which we selected into our selected list item
                    // Also we put the amount of selected items
                }
            }
        });
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActionMode != null) {
                    // Check ActionMode should be exist
                    if (mSelectedItems.contains(mItems.get(position))){
                        mSelectedItems.remove(mItems.get(position));
                        view.setSelected(false);
                        mViewsIsSelected.remove(view);
                        setDefaultColorView(holder.mCardView);
                        mActionMode.setTitle(mSelectedItems.size() + " selected");
                        // If our selected item's list contain the item which clicked
                        // we remove this item from the selected item's list and change card's background to default
                        // Also we put the amount of selected items
                    }
                    else {
                        mSelectedItems.add(mItems.get(position));
                        view.setSelected(true);
                        mViewsIsSelected.add(view);
                        setSelectedColorView(holder.mCardView);
                        mActionMode.setTitle(mSelectedItems.size() + " selected");
                        // If our selected item's list doesn't contain the item which clicked
                        // we put the item into the selected item's list
                        // Also we put the amount of selected items
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            MenuInflater menuInflater = actionMode.getMenuInflater();
            menuInflater.inflate(R.menu.action_mode_menu, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.delete:
                    if (mSelectedItems.isEmpty()) {
                        actionMode.finish();
                        return true;
                        // If we doesn't select items we finish ActionMode
                    }
                    else {
                        mAlertDialog = new AlertDialog.Builder(mContext, R.style.MyAlertDialogTheme)
                                .setCancelable(false)
                                .setTitle(R.string.title_dialog)
                                .setMessage(R.string.message_remove)
                                .setPositiveButton(R.string.positive_button, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        deleteItems();
                                        mActionMode.finish();
                                    }
                                })
                                .setNegativeButton(R.string.negative_button, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        mActionMode.finish();
                                    }
                                })
                                .show();
                        return true;
                        // We show dialog with positive and negative buttons
                        // If user click a positive button we delete selected items
                    }
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode actionMode) {
            mSelectedItems.clear();
            for (int i = 0; i < mViewsIsSelected.size(); i++) {
                setDefaultColorView((CardView) mViewsIsSelected.get(i));
            }
            mViewsIsSelected.clear();
            notifyDataSetChanged();
            mActionMode = null;
            // When ActionMode was destroy, we set default color to our selected items
            // And clear selected item's list
        }
    };

    private void setDefaultColorView(CardView view) {
        view.setCardBackgroundColor(mResources.getColor(R.color.colorCardView));
    }

    private void setSelectedColorView(CardView view) {
        view.setCardBackgroundColor(mResources.getColor(R.color.colorSelected));
    }

    private void deleteItems(){
        for (int j = 0; j < mSelectedItems.size(); j++) {
            int pos = mItems.indexOf(mSelectedItems.get(j));
            mItems.remove(mSelectedItems.get(j));
            mDataBaseDbHelper.deleteItem(mSelectedItems.get(j).getId());
            MyRecyclerViewAdapter.this.notifyItemRemoved(pos);
        }
        // Delete items if there are selecting
    }
}


