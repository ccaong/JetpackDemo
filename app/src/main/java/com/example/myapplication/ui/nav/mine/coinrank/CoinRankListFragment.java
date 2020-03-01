package com.example.myapplication.ui.nav.mine.coinrank;

import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.base.ScrollToTop;
import com.example.myapplication.databinding.FragmentListBinding;
import com.example.myapplication.http.bean.CoinBean;
import com.example.myapplication.http.bean.CoinRankBean;
import com.example.myapplication.ui.adapter.CommonAdapter;

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

    private void initRecycler() {

        CommonAdapter commonAdapter = new CommonAdapter<CoinBean.CoinDataBean>(R.layout.item_coin_rank, com.example.myapplication.BR.coinRank);
        mDataBinding.recycle.setAdapter(commonAdapter);
        mDataBinding.recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getCoinList().observe(this, new Observer<CoinRankBean>() {
            @Override
            public void onChanged(CoinRankBean coinBean) {
                if (coinBean.getCurPage() >= coinBean.getPageCount()) {
                    mDataBinding.mrlRefreshLayout.setLoadMore(false);
                    mDataBinding.mrlRefreshLayout.finishRefreshLoadMore();
                    Toast.makeText(getContext(), "没有更多数据了", Toast.LENGTH_SHORT).show();
                    return;
                }
                commonAdapter.onItemDatasChanged(coinBean.getDatas());
                mDataBinding.mrlRefreshLayout.finishRefresh();
                mDataBinding.mrlRefreshLayout.finishRefreshLoadMore();
            }
        });

    }

    @Override
    public void scrollToTop() {
        mDataBinding.recycle.smoothScrollToPosition(0);
    }
}
