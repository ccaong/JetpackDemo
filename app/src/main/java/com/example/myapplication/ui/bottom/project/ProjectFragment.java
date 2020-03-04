package com.example.myapplication.ui.bottom.project;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.navinterface.ScrollToTop;
import com.example.myapplication.config.Constants;
import com.example.myapplication.databinding.FragmentProjectViewPagerBinding;
import com.example.myapplication.bean.responsebean.WeChatBean;
import com.example.myapplication.ui.adapter.FmPagerAdapter;
import com.example.myapplication.ui.articlelist.ArticleListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author devel
 */
public class ProjectFragment extends BaseFragment<FragmentProjectViewPagerBinding, ProjectViewModel> implements ScrollToTop {

    private FmPagerAdapter adapter;
    private int pos;

    @Override
    protected void handleArguments(Bundle args) {
        super.handleArguments(args);
        pos = args.getInt(Constants.ParamCode.PARAM2, 0);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project_view_pager;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(ProjectViewModel.class);
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

        mViewModel.loadProject();
        initData();
    }

    private void initData() {
        mViewModel.getDataList().observe(this, new Observer<List<WeChatBean>>() {
            @Override
            public void onChanged(List<WeChatBean> weChatBeans) {
                initViewPager(weChatBeans);
            }
        });
    }

    private void initViewPager(List<WeChatBean> mList) {
        List<Fragment> fragments = new ArrayList<>();
        List<String> sTitle = new ArrayList<>();
        for (WeChatBean system : mList) {
            mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText(system.getName()));
            sTitle.add(system.getName());
            fragments.add(ArticleListFragment.newInstance(2, system.getId()));
        }
        mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPager);

        adapter = new FmPagerAdapter(getChildFragmentManager(), fragments, sTitle);
        mDataBinding.viewPager.setAdapter(adapter);
        mDataBinding.viewPager.setCurrentItem(pos);
    }

    @Override
    public void scrollToTop() {
        ArticleListFragment fragment = (ArticleListFragment) adapter.getCurrentFragment();
        if (fragment != null) {
            fragment.scrollToTop();
        }
    }
}
