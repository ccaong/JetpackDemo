package com.example.myapplication.base;


import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.cjj.MaterialRefreshLayout;
import com.example.myapplication.enums.RefreshState;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author : devel
 * @date : 2020/2/19 10:18
 * @desc :
 */
public class BindingAdapterUtil {

    @BindingAdapter("url")
    public static void setImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }


//    /**
//     * 设置RefreshLayout的刷新状态
//     *
//     * @param refreshLayout RefreshLayout
//     * @param refreshState  刷新状态
//     */
//    @BindingAdapter("refreshState")
//    public static void setRefreshState(MaterialRefreshLayout refreshLayout, RefreshState refreshState) {
//        if (refreshState == null) {
//            return;
//        }
//
//        switch (refreshState) {
//            case REFRESH_END:
//                refreshLayout.finishRefresh();
//                break;
//
//            case LOAD_MORE_END:
//                refreshLayout.finishRefreshLoadMore();
//                scrollToNextPosition(refreshLayout);
//                break;
//
//            default:
//                break;
//        }
//    }
//
//    /**
//     * 滚动到指定的下一页的第一条数据
//     *
//     * @param refreshLayout
//     */
//    private static void scrollToNextPosition(MaterialRefreshLayout refreshLayout) {
//        View child = refreshLayout.getChildAt(0);
//        if (child instanceof RecyclerView) {
//            RecyclerView recyclerView = (RecyclerView) child;
//            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
//            if (layoutManager instanceof LinearLayoutManager) {
//                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
//                recyclerView.smoothScrollToPosition(linearLayoutManager.findLastVisibleItemPosition() + 1);
//            }
//        }
//    }
//
//    /**
//     * 设置RefreshLayout的加载更多
//     *
//     * @param refreshLayout RefreshLayout
//     * @param hasMore       true表示还有更多，false表示没有更多了
//     */
//    @BindingAdapter("hasMore")
//    public static void setHasMore(MaterialRefreshLayout refreshLayout, Boolean hasMore) {
//        if (hasMore != null) {
//            refreshLayout.setLoadMore(hasMore);
//        }
//    }

    @BindingAdapter("visibility")
    public static void setViewVisibility(View view, Boolean visibility) {
        view.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }
}
