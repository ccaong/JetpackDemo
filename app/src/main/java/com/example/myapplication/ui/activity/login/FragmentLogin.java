package com.example.myapplication.ui.activity.login;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.LoginFragmentBinding;

import androidx.lifecycle.ViewModelProviders;

/**
 * @author : devel
 * @date : 2020/2/28 14:28
 * @desc : 登录
 */
public class FragmentLogin extends BaseFragment<LoginFragmentBinding, LoginViewModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.login_fragment;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(getActivity()).get(LoginViewModel.class);

    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
        mDataBinding.setActivity((LoginActivity) getActivity());
    }

    @Override
    protected void init() {

    }
}
