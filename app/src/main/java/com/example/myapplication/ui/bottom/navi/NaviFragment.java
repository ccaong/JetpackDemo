package com.example.myapplication.ui.bottom.navi;

import android.util.Log;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.NaviFragmentBinding;
import com.example.myapplication.http.bean.NavigationBean;
import com.example.myapplication.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.SimpleTabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

/**
 * @author devel
 */
public class NaviFragment extends BaseFragment<NaviFragmentBinding, NaviViewModel> implements VerticalTabLayout.OnTabSelectedListener {

    List<String> mTitlesList;

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
    protected void init() {
        mViewModel.loadNavigationData();

        initVerticalTabLayout();
        initContentRecyclerView();
        initDataChange();
    }

    public void initVerticalTabLayout() {
        //初始化Tab栏数据
        mTitlesList = new ArrayList<>();
        mDataBinding.verticalTabLayout.addOnTabSelectedListener(this);
    }

    private void initData() {
        mDataBinding.verticalTabLayout.setTabAdapter(new SimpleTabAdapter() {

            @Override
            public int getCount() {
                return mTitlesList.size();
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new ITabView.TabTitle.Builder()
                        .setContent(mTitlesList.get(position))
                        .setTextColor(CommonUtils.getColor(R.color.colorPrimary),
                                CommonUtils.getColor(R.color.text_black_87))
                        .setTextSize(16)
                        .build();
            }
        });
    }


    public void initContentRecyclerView() {

    }

    public void initDataChange() {
        mViewModel.getDataList().observe(this, new Observer<List<NavigationBean>>() {
            @Override
            public void onChanged(List<NavigationBean> navigationBeans) {
                for (NavigationBean baan : navigationBeans) {
                    mTitlesList.add(baan.getName());
                }
                initData();

            }
        });
    }

    @Override
    public void onTabSelected(TabView tab, int position) {

        Log.e("选中事项", "position" + position);
        Log.e("选中事项", "TabView" + tab.getTitleView().getText());
    }

    @Override
    public void onTabReselected(TabView tab, int position) {

    }
}
