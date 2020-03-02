package com.example.myapplication.ui.nav.square;

import android.os.Bundle;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.base.ScrollToTop;
import com.example.myapplication.common.Code;
import com.example.myapplication.databinding.FragmentListBinding;
import com.example.myapplication.http.bean.ArticleBean;
import com.example.myapplication.http.bean.ArticleListBean;
import com.example.myapplication.ui.activity.web.DetailsActivity;
import com.example.myapplication.ui.adapter.CommonAdapter;

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
        refreshLayout = mDataBinding.mrlRefreshLayout;

        mViewModel.loadData();

        initRefreshLayout();

        initRecyclerView();
    }

    /**
     * 下拉刷新
     */
    private void initRefreshLayout() {
        mDataBinding.mrlRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                mDataBinding.mrlRefreshLayout.setLoadMore(true);
                mViewModel.refreshData(true);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
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
                        bundle.putInt(Code.ParamCode.PARAM1, itemData.getUserId());
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
                    mDataBinding.mrlRefreshLayout.setLoadMore(false);
                }
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