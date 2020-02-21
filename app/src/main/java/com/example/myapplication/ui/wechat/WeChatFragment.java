package com.example.myapplication.ui.wechat;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.FragmentViewPagerBinding;
import com.example.myapplication.ui.adapter.ArticleListPagerAdapter;

import androidx.lifecycle.ViewModelProviders;


/**
 * @author devel
 */
public class WeChatFragment extends BaseFragment<FragmentViewPagerBinding, WeChatViewModel> {

    private ArticleListPagerAdapter mPagerAdapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_view_pager;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(WeChatViewModel.class);
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
        mViewModel.loadWeChatList();

        mPagerAdapter = new ArticleListPagerAdapter(getChildFragmentManager());
        mDataBinding.viewPager.setAdapter(mPagerAdapter);
        mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPager);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPagerAdapter != null) {
            mPagerAdapter.release();
        }
    }
}