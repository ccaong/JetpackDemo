package com.example.myapplication.ui.bottom.project;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.base.ScrollToTop;
import com.example.myapplication.common.Code;
import com.example.myapplication.databinding.FragmentListBinding;
import com.example.myapplication.databinding.FragmentProjectViewPagerBinding;
import com.example.myapplication.databinding.FragmentViewPagerBinding;
import com.example.myapplication.http.bean.WeChatBean;
import com.example.myapplication.ui.adapter.ArticleListPagerAdapter;
import com.example.myapplication.ui.adapter.FmPagerAdapter;
import com.example.myapplication.ui.articlelist.ArticleListFragment;
import com.example.myapplication.ui.bottom.wechat.WeChatViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author devel
 */
public class ProjectFragment extends BaseFragment<FragmentProjectViewPagerBinding, ProjectViewModel> implements ScrollToTop {

    private FmPagerAdapter adapter;
    private int pos;

    @Override
    protected void handleArguments(Bundle args) {
        super.handleArguments(args);
        pos = args.getInt(Code.ParamCode.PARAM2, 0);
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
            fragments.add(ArticleListFragment.newInstance(1, system.getId()));
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
