package com.example.myapplication.ui.system;

import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;


/**
 * @author devel
 */
public class SystemFragment extends BaseFragment {


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SystemViewModel.class);
    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {

    }
}