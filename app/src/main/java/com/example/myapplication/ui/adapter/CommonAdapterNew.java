package com.example.myapplication.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.databinding.ItemArticleNewBinding;
import com.example.myapplication.http.bean.ArticleBean;
import com.example.myapplication.ui.wechat.wxcontent.ArticleViewModel;

import java.util.List;

/**
 * 通用的Adapter
 *
 * @author devel
 */
public class CommonAdapterNew<T,DB extends ViewDataBinding> extends RecyclerView.Adapter<CommonViewHolder> {

    protected DB mDataBinding;


    private List<T> mList;
    /**
     * 布局id
     */
    private int defaultLayout;


    public CommonAdapterNew(int defaultLayout) {
        this.defaultLayout = defaultLayout;
    }

    public CommonAdapterNew(List<T> list, int defaultLayout) {
        this.mList = list;
        this.defaultLayout = defaultLayout;
    }

    public int getItemLayout(T itemData) {
        // TODO: 2020/2/24 可以根据不同的数据类型，返回不同的布局文件
        return defaultLayout;
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

    @Override
    public int getItemViewType(int position) {
        return getItemLayout(mList.get(position));
    }


    @NonNull
    @Override
    public CommonViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // TODO: 2020/2/24 可以根据不同的viewType，返回不同的ViewHolder
        mDataBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), viewType, viewGroup, false);
        return new CommonViewHolder(mDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CommonViewHolder commonViewHolder, int position) {
        //绑定数据

        ItemArticleNewBinding newBinding = (ItemArticleNewBinding) commonViewHolder.binding;
        ArticleViewModel articleViewModel = new ArticleViewModel();
        articleViewModel.setBaseModel((ArticleBean) mList.get(position));
        newBinding.setViewModel(articleViewModel);

//        commonViewHolder.binding.setVariable(brId, mList.get(position));
        addListener(commonViewHolder.binding.getRoot(), mList.get(position), position);
        //防止数据闪烁
        commonViewHolder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }

}
