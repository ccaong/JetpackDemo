package com.example.myapplication.ui.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.databinding.ItemArticleNewBinding;
import com.example.myapplication.http.bean.ArticleBean;
import com.example.myapplication.http.bean.home.HomeData;
import com.example.myapplication.ui.wechat.wxcontent.ArticleViewModel;

import java.util.List;

/**
 * 通用的Adapter
 *
 * @author devel
 */
public class CommonAdapterNew<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private List<T> mList;
    /**
     * 布局id
     */
    private int defaultLayout;
    /**
     *
     */
    private int brId;


    public CommonAdapterNew(int defaultLayout, int brId) {
        this.defaultLayout = defaultLayout;
        this.brId = brId;
    }

    public CommonAdapterNew(List<T> list, int defaultLayout, int brId) {
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
        // TODO: 2020/2/24 可以根据不同的数据类型，返回不同的布局文件
        if (itemData instanceof ArticleBean) {
            return defaultLayout;
        } else {
            return R.layout.item_home_banner;
        }
    }


    @Override
    public int getItemViewType(int position) {
        return getItemLayout(mList.get(position));
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // TODO: 2020/2/24 可以根据不同的viewType，返回不同的ViewHolder
        ViewDataBinding dataBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), viewType, viewGroup, false);
        return new CommonViewHolder(dataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder commonViewHolder, int position) {
        HomeData homeData = (HomeData) mList.get(position);

        if (homeData.getArticleList() != null) {
            //绑定数据
            ((CommonViewHolder) commonViewHolder).binding.setVariable(brId, homeData.getArticleList());
        } else {
            ((CommonViewHolder) commonViewHolder).binding.setVariable(com.example.myapplication.BR.bannerData, homeData.getBannerData());
        }

        addListener(((CommonViewHolder) commonViewHolder).binding.getRoot(), mList.get(position), position);
        //防止数据闪烁
//        ((CommonViewHolder) commonViewHolder).binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
}
