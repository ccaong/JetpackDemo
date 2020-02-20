package com.example.myapplication.ui.wechat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.FragmentViewPagerBinding;
import com.example.myapplication.entity.WeChatBean;
import com.example.myapplication.entity.WeChatListEntity;
import com.example.myapplication.ui.adapter.FmPagerAdapter;

import java.util.ArrayList;
import java.util.List;


public class WeChatFragment extends BaseFragment<FragmentViewPagerBinding, WeChatViewModel> {

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

    }

    @Override
    protected void init() {
        mViewModel.loadWeChatList();

        List<Fragment> fragments = new ArrayList<>();
        List<String> mTitle = new ArrayList<>();

        mViewModel.getWeChatList().observe(this, new Observer<WeChatListEntity>() {
            @Override
            public void onChanged(WeChatListEntity weChatListEntity) {

                if (weChatListEntity != null && weChatListEntity.getData() != null) {

                    for (WeChatBean weChatBean : weChatListEntity.getData()) {
                        mDataBinding.tabLayout.addTab(mDataBinding.tabLayout.newTab().setText(weChatBean.getName()));
                        WeChatContentListFragment fragment = WeChatContentListFragment.newInstance(weChatBean.getId());
                        fragments.add(fragment);
                        mTitle.add(weChatBean.getName());
                    }
                    mDataBinding.tabLayout.setupWithViewPager(mDataBinding.viewPager, false);
                    mDataBinding.viewPager.setAdapter(
                            new FmPagerAdapter(getChildFragmentManager(), fragments, mTitle));
                }

            }
        });
    }

    private void initTablayout() {

    }

    private void initFragment() {

    }
}