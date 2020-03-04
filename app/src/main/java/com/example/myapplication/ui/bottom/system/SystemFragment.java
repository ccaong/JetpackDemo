package com.example.myapplication.ui.bottom.system;

import android.os.Bundle;
import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.navinterface.ScrollToTop;
import com.example.myapplication.config.Constants;
import com.example.myapplication.databinding.FragmentListBinding;
import com.example.myapplication.bean.responsebean.WeChatBean;
import com.example.myapplication.ui.adapter.SystemAdapter;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import java.io.Serializable;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;


/**
 * @author devel
 */
public class SystemFragment extends BaseFragment<FragmentListBinding, SystemViewModel> implements ScrollToTop {


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
        mViewModel = ViewModelProviders.of(this).get(SystemViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {

        mViewModel.loadData();

        initRefreshLayout();
        setRecycleView();
    }

    /**
     * 下拉刷新
     */
    private void initRefreshLayout() {
        mDataBinding.refreshLayout.setEnableLoadMore(false);

        mDataBinding.refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mViewModel.refreshData();
            }
        });
    }

    private void setRecycleView() {
        SystemAdapter commonAdapter = new SystemAdapter(mViewModel.getSystemList().getValue()) {
            @Override
            public void addListener(View root, WeChatBean itemData, int position) {
                super.addListener(root, itemData, position);
                root.findViewById(R.id.card_view).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.ParamCode.PARAM2, 0);
                        bundle.putSerializable(Constants.ParamCode.PARAM1, (Serializable) itemData.getChildren());

                        NavHostFragment.findNavController(SystemFragment.this).navigate(R.id.systemChildrenFragment, bundle);
                    }
                });
            }

            @Override
            public void addChildrenListener(View root, WeChatBean itemData, List<WeChatBean> parentSystem, int position) {
                super.addChildrenListener(root, itemData, parentSystem, position);

                Bundle bundle = new Bundle();
                bundle.putInt(Constants.ParamCode.PARAM2, position);
                bundle.putSerializable(Constants.ParamCode.PARAM1, (Serializable) parentSystem);
                NavHostFragment.findNavController(SystemFragment.this).navigate(R.id.systemChildrenFragment, bundle);

            }
        };
        mDataBinding.recycle.setAdapter(commonAdapter);
        mDataBinding.recycle.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getSystemList().observe(this, new Observer<List<WeChatBean>>() {
            @Override
            public void onChanged(List<WeChatBean> systemList) {
                commonAdapter.onItemDatasChanged(systemList);
                mDataBinding.refreshLayout.finishRefresh();
            }
        });
    }

    @Override
    public void scrollToTop() {
        mDataBinding.recycle.smoothScrollToPosition(0);
    }
}