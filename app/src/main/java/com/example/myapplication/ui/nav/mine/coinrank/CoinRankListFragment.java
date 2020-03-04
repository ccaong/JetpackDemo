package com.example.myapplication.ui.nav.mine.coinrank;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.navinterface.ScrollToTop;
import com.example.myapplication.databinding.FragmentListBinding;
import com.example.myapplication.bean.responsebean.CoinBean;
import com.example.myapplication.bean.responsebean.CoinRankBean;
import com.example.myapplication.ui.adapter.CommonAdapter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author : devel
 * @date : 2020/2/26 16:27
 * @desc :
 */
public class CoinRankListFragment extends BaseFragment<FragmentListBinding, CoinRankViewModel>
        implements ScrollToTop {

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

        mViewModel = ViewModelProviders.of(this).get(CoinRankViewModel.class);
    }

    @Override
    protected void bindViewModel() {

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

        CommonAdapter commonAdapter = new CommonAdapter<CoinBean.CoinDataBean>(R.layout.item_coin_rank, com.example.myapplication.BR.coinRank);
        mDataBinding.recycle.setAdapter(commonAdapter);
        mDataBinding.recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getCoinList().observe(this, new Observer<CoinRankBean>() {
            @Override
            public void onChanged(CoinRankBean coinBean) {
                if (coinBean.getCurPage() >= coinBean.getPageCount()) {
                    mDataBinding.refreshLayout.finishLoadMoreWithNoMoreData();
                }
                mDataBinding.refreshLayout.finishRefresh();
                mDataBinding.refreshLayout.finishLoadMore();
                commonAdapter.onItemDatasChanged(coinBean.getDatas());
            }
        });

    }

    @Override
    public void scrollToTop() {
        mDataBinding.recycle.smoothScrollToPosition(0);
    }
}
