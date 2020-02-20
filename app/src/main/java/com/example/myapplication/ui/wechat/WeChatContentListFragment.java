package com.example.myapplication.ui.wechat;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.common.Code;
import com.example.myapplication.databinding.FragmentListBinding;

public class WeChatContentListFragment extends BaseFragment<FragmentListBinding, WechatContentListViewHolder> {

    private int id;

    public static WeChatContentListFragment newInstance(int id) {
        WeChatContentListFragment weChatContentListFragment = new WeChatContentListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Code.ParamCode.PARAM1, id);
        weChatContentListFragment.setArguments(bundle);
        return weChatContentListFragment;
    }

    @Override
    protected void handleArguments(Bundle args) {
        super.handleArguments(args);
        id = args.getInt(Code.ParamCode.PARAM1);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initViewModel() {

        mViewModel = ViewModelProviders.of(this).get(WechatContentListViewHolder.class);
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {

    }
}
