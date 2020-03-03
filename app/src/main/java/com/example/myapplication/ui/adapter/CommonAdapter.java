package com.example.myapplication.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 通用的Adapter
 *
 * @author devel
 */
public class CommonAdapter<T> extends RecyclerView.Adapter<CommonViewHolder> {


    private List<T> mList;
    /**
     * 布局id
     */
    private int defaultLayout;
    /**
     *
     */
    private int brId;


    public CommonAdapter(int defaultLayout, int brId) {
        this.defaultLayout = defaultLayout;
        this.brId = brId;
    }

    public CommonAdapter(List<T> list, int defaultLayout, int brId) {
        this.mList = list;
        this.defaultLayout = defaultLayout;
        this.brId = brId;
    }

    /**
     * 添加监听回调
     *
     * @param root
     * @param itemData
     * @param position
     */
    public void addListener(View root, T itemData, int position) {
    }

    /**
     * 改变数据
     *
     * @param newItemDatas
     */
    public void onItemDatasChanged(List<T> newItemDatas) {
        this.mList = newItemDatas;
        notifyDataSetChanged();
    }

    /**
     * 改变部分数据
     *
     * @param newItemDatas
     * @param positionStart
     * @param itemCount
     */
    public void onItemRangeChanged(List<T> newItemDatas, int positionStart, int itemCount) {
        this.mList = newItemDatas;
        notifyItemRangeChanged(positionStart, itemCount);
    }

    /**
     * 插入数据
     *
     * @param newItemDatas
     * @param positionStart
     * @param itemCount
     */
    protected void onItemRangeInserted(List<T> newItemDatas, int positionStart, int itemCount) {
        this.mList = newItemDatas;
        notifyItemRangeInserted(positionStart, itemCount);
    }

    /**
     * 移除某个数据
     *
     * @param newItemDatas
     * @param positionStart
     * @param itemCount
     */
    protected void onItemRangeRemoved(List<T> newItemDatas, int positionStart, int itemCount) {
        this.mList = newItemDatas;
        notifyItemRangeRemoved(positionStart, itemCount);
    }

    public int getItemLayout(T itemData) {
        return defaultLayout;
    }


    @Override
    public int getItemViewType(int position) {
        return getItemLayout(mList.get(position));
    }


    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), viewType, viewGroup, false);
        return new CommonViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder commonViewHolder, int position) {

        //绑定数据
        commonViewHolder.binding.setVariable(brId, mList.get(position));
        addListener(commonViewHolder.binding.getRoot(), mList.get(position), position);
        //防止数据闪烁
        commonViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

}
