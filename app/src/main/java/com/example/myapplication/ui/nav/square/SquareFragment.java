package com.example.myapplication.ui.nav.square;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.navinterface.ScrollToTop;
import com.example.myapplication.config.Constants;
import com.example.myapplication.databinding.FragmentListBinding;
import com.example.myapplication.bean.responsebean.ArticleBean;
import com.example.myapplication.bean.responsebean.ArticleListBean;
import com.example.myapplication.ui.activity.web.DetailsActivity;
import com.example.myapplication.ui.adapter.CommonAdapter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

public class SquareFragment extends BaseFragment<FragmentListBinding, SquareViewModel> implements ScrollToTop {

    @Override
    protected boolean isSupportLoad() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SquareViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {
        mViewModel.loadData();

        initRefreshLayout();

        initRecyclerView();
    }

    /**
     * 下拉刷新
     */
    private void initRefreshLayout() {
        mDataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mViewModel.refreshData(true);
            }
        });
        mDataBinding.refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                mViewModel.refreshData(false);
            }
        });
    }

    private void initRecyclerView() {

        CommonAdapter commonAdapter = new CommonAdapter<ArticleBean>(R.layout.item_article, com.example.myapplication.BR.articleBean) {
            @Override
            public void addListener(View root, ArticleBean itemData, int position) {
                super.addListener(root, itemData, position);
                root.findViewById(R.id.card_view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DetailsActivity.start(getActivity(), itemData.getLink());
                    }
                });

                root.findViewById(R.id.iv_collect).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        itemData.setCollect(!itemData.isCollect());
                        notifyDataSetChanged();
                        mViewModel.changeArticleCollect(itemData.isCollect(), itemData.getId());
                    }
                });
                root.findViewById(R.id.tv_author).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.ParamCode.PARAM1, itemData.getUserId());
                        NavHostFragment.findNavController(SquareFragment.this).navigate(R.id.nav_share, bundle);
                    }
                });
            }
        };
        mDataBinding.recycle.setAdapter(commonAdapter);
        mDataBinding.recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getArticleList().observe(this, new Observer<ArticleListBean>() {
            @Override
            public void onChanged(ArticleListBean articleListBean) {
                if (articleListBean.getCurPage() >= articleListBean.getPageCount()) {
                    mDataBinding.refreshLayout.finishLoadMoreWithNoMoreData();
                }
                mDataBinding.refreshLayout.finishRefresh();
                mDataBinding.refreshLayout.finishLoadMore();
                commonAdapter.onItemDatasChanged(articleListBean.getDatas());
            }
        });
    }

    /**
     * 滚动到顶部
     */
    public void scrollToTop() {
        mDataBinding.recycle.smoothScrollToPosition(0);
    }

}