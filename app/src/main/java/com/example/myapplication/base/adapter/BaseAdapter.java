package com.example.myapplication.base.adapter;


import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter的基类
 */
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected ObservableList<Object> mDataList;

    public BaseAdapter(@NonNull ObservableList<Object> dataList) {
        mDataList = dataList;
        initCallback();
    }

    private void initCallback() {
        mDataList.addOnListChangedCallback(new ObservableList.OnListChangedCallback<ObservableList<Object>>() {

            @Override
            public void onChanged(ObservableList<Object> sender) {
                notifyDataSetChanged();
            }

            @Override
            public void onItemRangeChanged(ObservableList<Object> sender, int positionStart, int itemCount) {
                notifyItemRangeChanged(positionStart, itemCount);
            }

            @Override
            public void onItemRangeInserted(ObservableList<Object> sender, int positionStart, int itemCount) {
                notifyItemRangeInserted(positionStart, itemCount);
            }

            @Override
            public void onItemRangeMoved(ObservableList<Object> sender, int fromPosition, int toPosition, int itemCount) {
                if (itemCount == 1) {
                    notifyItemMoved(fromPosition, toPosition);
                } else {
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onItemRangeRemoved(ObservableList<Object> sender, int positionStart, int itemCount) {
                notifyItemRangeRemoved(positionStart, itemCount);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList != null ? mDataList.size() : 0;
    }
}
