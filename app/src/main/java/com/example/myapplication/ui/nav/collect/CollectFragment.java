package com.example.myapplication.ui.nav.collect;

import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.BR;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.FragmentListBinding;
import com.example.myapplication.bean.responsebean.CollectArticleBean;
import com.example.myapplication.ui.activity.web.DetailsActivity;
import com.example.myapplication.ui.adapter.CommonAdapter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author devel
 */
public class CollectFragment extends BaseFragment<FragmentListBinding, CollectViewModel> {

    private CommonAdapter commonAdapter;

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
        mViewModel = ViewModelProviders.of(this).get(CollectViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setCollectViewModel(mViewModel);
    }

    @Override
    protected void init() {
        mViewModel.loadData();
        initRefreshLayout();
        initRecyclerView();
        initData();
    }

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
        commonAdapter = new CommonAdapter<CollectArticleBean.CollectBean>(R.layout.item_collect_article, BR.collectArticle) {
            @Override
            public void addListener(View root, CollectArticleBean.CollectBean itemData, int position) {
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
                        ((ImageView) root.findViewById(R.id.iv_collect)).setImageResource(R.mipmap.ic_collectbroken);
                        mViewModel.collectCancel(itemData);
                    }
                });

            }
        };
        mDataBinding.recycle.setAdapter(commonAdapter);
        mDataBinding.recycle.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initData() {
        mViewModel.getArticleList().observe(this, new Observer<CollectArticleBean>() {
            @Override
            public void onChanged(CollectArticleBean collectArticleBean) {

                if (collectArticleBean.getCurPage() >= collectArticleBean.getPageCount()) {
                    mDataBinding.refreshLayout.finishLoadMoreWithNoMoreData();
                }
                mDataBinding.refreshLayout.finishRefresh();
                mDataBinding.refreshLayout.finishLoadMore();

                if (commonAdapter != null) {
                    commonAdapter.onItemDatasChanged(collectArticleBean.getDatas());
                }
            }
        });
    }
}