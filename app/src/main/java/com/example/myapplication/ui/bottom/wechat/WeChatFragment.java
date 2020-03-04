package com.example.myapplication.ui.bottom.wechat;

import android.os.Bundle;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.navinterface.ScrollToTop;
import com.example.myapplication.config.Constants;
import com.example.myapplication.databinding.FragmentViewPagerBinding;
import com.example.myapplication.bean.responsebean.WeChatBean;
import com.example.myapplication.ui.adapter.ArticleListPagerAdapter;

import java.util.List;

import androidx.lifecycle.ViewModelProviders;


/**
 * @author devel
 */
public class WeChatFragment extends BaseFragment<FragmentViewPagerBinding, WeChatViewModel> implements ScrollToTop {

    private ArticleListPagerAdapter mPagerAdapter;

    private List<WeChatBean> mList;
    private int pos;

    @Override
    protected void handleArguments(Bundle args) {
        super.handleArguments(args);
        pos = args.getInt(Constants.ParamCode.PARAM2, 0);
        mList = (List<WeChatBean>) args.getSerializable(Constants.ParamCode.PARAM1);
    }


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
        if (mList != null) {
            mViewModel.setDataList(mList);
        } else {
            mViewModel.loadWeChatList();
        }

        mPagerAdapter = new ArticleListPagerAdapter(getChildFragmentManager());
        mDataBinding.viewPager.setAdapter(mPagerAdapter);
        mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPager);

        mDataBinding.viewPager.setCurrentItem(pos);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPagerAdapter != null) {
            mPagerAdapter.release();
        }
    }

    @Override
    public void scrollToTop() {

    }
}