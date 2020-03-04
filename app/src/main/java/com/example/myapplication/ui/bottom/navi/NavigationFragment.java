package com.example.myapplication.ui.bottom.navi;

import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.navinterface.ScrollToTop;
import com.example.myapplication.databinding.NaviFragmentBinding;
import com.example.myapplication.bean.responsebean.ArticleBean;
import com.example.myapplication.bean.responsebean.NavigationBean;
import com.example.myapplication.ui.activity.web.DetailsActivity;
import com.example.myapplication.ui.adapter.NavigationAdapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * @author devel
 */
public class NavigationFragment extends BaseFragment<NaviFragmentBinding, NaviViewModel>
        implements ScrollToTop, VerticalTabLayout.OnTabSelectedListener {

    private OnScrollListener mScrollListener;

    private NavigationAdapter commonAdapter;

    private LinearLayoutManager mLayoutManager;

    private boolean mNeedScroll = false;

    private int mToPosition = -1;


    @Override
    protected int getLayoutResId() {
        return R.layout.navi_fragment;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(NaviViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
    }

    @Override
    protected boolean isSupportLoad() {
        return true;
    }

    @Override
    protected void init() {
        mViewModel.loadNavigationData();
        initDataChange();

        initVerticalTabLayout();
        initContentRecyclerView();
    }

    public void initDataChange() {
        mViewModel.getDataList().observe(this, new Observer<List<NavigationBean>>() {
            @Override
            public void onChanged(List<NavigationBean> navigationBeans) {
                commonAdapter.onItemDatasChanged(navigationBeans);
            }
        });
    }

    public void initVerticalTabLayout() {
        //初始化Tab栏数据
        mDataBinding.verticalTabLayout.addOnTabSelectedListener(this);
    }

    public void initContentRecyclerView() {
        mLayoutManager = new LinearLayoutManager(getContext());
        mScrollListener = new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mNeedScroll) {
                        mNeedScroll = false;
                        smoothScrollToPosition(mToPosition);
                    } else {
                        setTabSelected();
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

            }
        };

        commonAdapter = new NavigationAdapter(mViewModel.getDataList().getValue()) {
            @Override
            public void addChildrenListener(View root, ArticleBean itemData, int position) {
                super.addChildrenListener(root, itemData, position);
                DetailsActivity.start(getActivity(), itemData.getLink());
            }
        };
        mDataBinding.recyclerView.addOnScrollListener(mScrollListener);
        mDataBinding.recyclerView.setLayoutManager(mLayoutManager);
        mDataBinding.recyclerView.setAdapter(commonAdapter);
    }

    private void setTabSelected() {
        int firstPosition = mLayoutManager.findFirstVisibleItemPosition();
        mDataBinding.verticalTabLayout.setTabSelected(firstPosition, false);
    }

    @Override
    public void onTabSelected(TabView tab, int position) {
        smoothScrollToPosition(position);
    }

    @Override
    public void onTabReselected(TabView tab, int position) {

    }


    /**
     * 滚动到指定位置
     *
     * @param position
     */
    private void smoothScrollToPosition(int position) {
        int firstPosition = mLayoutManager.findFirstVisibleItemPosition();
        int lastPosition = mLayoutManager.findLastVisibleItemPosition();

        if (position < firstPosition) {
            mDataBinding.recyclerView.smoothScrollToPosition(position);
        } else if (position <= lastPosition) {
            int movePosition = position - firstPosition;
            if (movePosition >= 0 && movePosition < mLayoutManager.getChildCount()) {
                int top = mLayoutManager.getChildAt(movePosition).getTop();
                mDataBinding.recyclerView.smoothScrollBy(0, top);
            }
        } else {
            mDataBinding.recyclerView.smoothScrollToPosition(position);
            mToPosition = position;
            mNeedScroll = true;
        }
    }

    @Override
    public void scrollToTop() {
        mDataBinding.recyclerView.smoothScrollToPosition(0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mDataBinding.verticalTabLayout.removeOnTabSelectedListener(this);
        mDataBinding.recyclerView.removeOnScrollListener(mScrollListener);
    }


}
