package com.example.myapplication.ui.nav.mine.coin;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.FragmentListBinding;
import com.example.myapplication.bean.responsebean.CoinBean;
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
public class CoinListFragment extends BaseFragment<FragmentListBinding, CoinViewModel> {


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(CoinViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected boolean isSupportLoad() {
        return true;
    }

    @Override
    protected void init() {
        mViewModel.loadData();
        initRecycler();
        initRefreshLayout();
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

    private void initRecycler() {

        CommonAdapter commonAdapter = new CommonAdapter<CoinBean.CoinDataBean>(R.layout.item_coin_layout, com.example.myapplication.BR.coinData);
        mDataBinding.recycle.setAdapter(commonAdapter);
        mDataBinding.recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getCoinList().observe(this, new Observer<CoinBean>() {
            @Override
            public void onChanged(CoinBean coinBean) {
                if (coinBean.getCurPage() >= coinBean.getPageCount()) {
                    mDataBinding.refreshLayout.finishLoadMoreWithNoMoreData();
                }
                mDataBinding.refreshLayout.finishRefresh();
                mDataBinding.refreshLayout.finishLoadMore();

                commonAdapter.onItemDatasChanged(coinBean.getDatas());
            }
        });

    }
}
