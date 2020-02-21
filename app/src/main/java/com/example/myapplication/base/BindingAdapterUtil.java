package com.example.myapplication.base;


import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.example.myapplication.base.adapter.BasePagerAdapter;
import com.example.myapplication.entity.ArticleBean;
import com.example.myapplication.enums.RefreshState;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

/**
 * @author : devel
 * @date : 2020/2/19 10:18
 * @desc :
 */
public class BindingAdapterUtil {


    /**
     * 设置ViewPager的数据列表
     *
     * @param viewPager ViewPager
     * @param dataList  数据列表
     * @param <T>       数据类型
     */
    @SuppressWarnings("unchecked")
    @BindingAdapter("app:dataList")
    public static <T> void setDataList(ViewPager viewPager, List<T> dataList) {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter instanceof BasePagerAdapter) {
            BasePagerAdapter basePagerAdapter = (BasePagerAdapter) adapter;
            basePagerAdapter.setDataList(dataList);
        }
    }

    /**
     * 设置RefreshLayout的刷新状态
     *
     * @param refreshLayout RefreshLayout
     * @param refreshState  刷新状态
     */
    @BindingAdapter("refreshState")
    public static void setRefreshState(MaterialRefreshLayout refreshLayout, RefreshState refreshState) {
        if (refreshState == null) {
            return;
        }
        switch (refreshState) {
            case REFRESH_END:
                refreshLayout.finishRefresh();
                break;

            case LOAD_MORE_END:
                refreshLayout.finishRefreshLoadMore();
                break;

            default:
                break;
        }
    }

    /**
     * 设置RefreshLayout的加载更多
     *
     * @param refreshLayout RefreshLayout
     * @param hasMore       true表示还有更多，false表示没有更多了
     */
    @BindingAdapter("hasMore")
    public static void setHasMore(MaterialRefreshLayout refreshLayout, Boolean hasMore) {
        if (hasMore != null) {
            refreshLayout.setLoadMore(hasMore);
        }
    }


    @BindingAdapter("url")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }


    @BindingAdapter("visibility")
    public static void setViewVisibility(View view, Boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter("articleTag")
    public static void setArticleTag(TextView view, ArticleBean articleBean) {
        if (articleBean != null && articleBean.getTags() != null) {
            if (articleBean.getTags().size() == 1) {
                view.setText(articleBean.getTags().get(0).getName());
            } else if (articleBean.getTags().size() >= 2) {
                String str = articleBean.getTags().get(0).getName() + articleBean.getTags().get(1).getName();
                view.setText(str);
            } else {
                view.setText("其他");
            }
        }
    }
}
