package com.example.myapplication.ui.bottom.project;

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
import com.example.myapplication.common.Code;
import com.example.myapplication.databinding.FragmentListBinding;
import com.example.myapplication.databinding.FragmentProjectViewPagerBinding;
import com.example.myapplication.databinding.FragmentViewPagerBinding;
import com.example.myapplication.http.bean.WeChatBean;
import com.example.myapplication.ui.adapter.ArticleListPagerAdapter;
import com.example.myapplication.ui.bottom.wechat.WeChatViewModel;

import java.util.List;

public class ProjectFragment extends BaseFragment<FragmentProjectViewPagerBinding, ProjectViewModel> {

    private ArticleListPagerAdapter mPagerAdapter;

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
}
