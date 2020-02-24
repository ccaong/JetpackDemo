package com.example.myapplication.base.adapter;


import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Adapter的基类
 * @author devel
 */
public abstract class BaseAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements LifecycleOwner {

    protected LiveData<List<Object>> mDataList;

    public BaseAdapter(@NonNull LiveData<List<Object>> dataList) {
        mDataList = dataList;
        initCallback();
    }

    private void initCallback() {

        mDataList.observe(this, new Observer<Object>() {
            @Override
            public void onChanged(Object o) {
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.getValue() != null ? mDataList.getValue().size() : 0;
    }
}
