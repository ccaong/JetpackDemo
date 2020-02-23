package com.example.myapplication.ui.wechat.wxcontent;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.common.Code;
import com.example.myapplication.databinding.FragmentListBinding;
import com.example.myapplication.http.bean.ArticleBean;
import com.example.myapplication.http.bean.ArticleListBean;
import com.example.myapplication.ui.activity.web.DetailsActivity;
import com.example.myapplication.ui.adapter.CommonAdapter;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author devel
 */
public class WeChatContentListFragment extends BaseFragment<FragmentListBinding, WeChatContentListViewModel> {

    private int id;

    public static WeChatContentListFragment newInstance(int id) {
        WeChatContentListFragment weChatContentListFragment = new WeChatContentListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Code.ParamCode.PARAM1, id);
        weChatContentListFragment.setArguments(bundle);
        return weChatContentListFragment;
    }

    @Override
    protected void handleArguments(Bundle args) {
        super.handleArguments(args);
        id = args.getInt(Code.ParamCode.PARAM1);
    }

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

        mViewModel = ViewModelProviders.of(this).get(WeChatContentListViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {
        mDataBinding.mrlRefreshLayout.setLoadMore(true);
        mViewModel.setId(id);
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
                mViewModel.mRefresh = true;
                mViewModel.refreshData();

            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                mViewModel.mRefresh = true;
                mViewModel.loadMoreData();
            }
        });
    }

    private void initRecyclerView() {

        CommonAdapter commonAdapter = new CommonAdapter<ArticleBean>(R.layout.item_article, BR.articleBean) {
            @Override
            public void addListener(View root, ArticleBean itemData, int position) {
                super.addListener(root, itemData, position);
                root.findViewById(R.id.card_view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DetailsActivity.start(getActivity(),itemData.getLink());
                    }
                });
            }
        };
        mDataBinding.recycle.setAdapter(commonAdapter);
        mDataBinding.recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getArticleList().observe(this, new Observer<ArticleListBean>() {
            @Override
            public void onChanged(ArticleListBean articleListBean) {

                if (articleListBean.isOver()) {
                    Toast.makeText(getContext(), "没有更多数据了！", Toast.LENGTH_SHORT).show();
                    mDataBinding.mrlRefreshLayout.finishRefreshLoadMore();
                    return;
                }
                commonAdapter.onItemDatasChanged(articleListBean.getDatas());
                mDataBinding.mrlRefreshLayout.finishRefresh();
                mDataBinding.mrlRefreshLayout.finishRefreshLoadMore();
            }
        });
    }
}
