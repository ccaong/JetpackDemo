package com.example.myapplication.ui.nav.todo;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.FragmentListBinding;

public class ToDoFragment extends BaseFragment<FragmentListBinding, ToDoViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initViewModel() {

        mViewModel = ViewModelProviders.of(this).get(ToDoViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setToDoViewModel(mViewModel);
    }

    @Override
    protected void init() {


    }

}
