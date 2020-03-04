package com.example.myapplication.ui.bottom.system.systemchildren;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.navinterface.ScrollToTop;
import com.example.myapplication.config.Constants;
import com.example.myapplication.databinding.FragmentViewPagerBinding;
import com.example.myapplication.bean.responsebean.WeChatBean;
import com.example.myapplication.ui.adapter.FmPagerAdapter;
import com.example.myapplication.ui.articlelist.ArticleListFragment;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author : devel
 * @date : 2020/2/27 9:46
 * @desc :
 */
public class SystemChildrenFragment extends BaseFragment<FragmentViewPagerBinding, SystemChildrenViewModel> implements ScrollToTop {

    private FmPagerAdapter adapter;
    private List<WeChatBean> mList;
    private int pos;

    @Override
    protected void handleArguments(Bundle args) {
        super.handleArguments(args);
        pos = args.getInt(Constants.ParamCode.PARAM2);
        mList = (List<WeChatBean>) args.getSerializable(Constants.ParamCode.PARAM1);
    }


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_view_pager;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SystemChildrenViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {

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
