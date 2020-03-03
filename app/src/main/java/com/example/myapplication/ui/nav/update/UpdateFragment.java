package com.example.myapplication.ui.nav.update;

import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.UpdateFragmentBinding;
import com.example.myapplication.entity.UpdatePlan;
import com.example.myapplication.ui.adapter.CommonAdapter;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

public class UpdateFragment extends BaseFragment<UpdateFragmentBinding, UpdateViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.update_fragment;
    }

    @Override
    protected void initViewModel() {

    }

    @Override
    protected void bindViewModel() {

    }

    @Override
    protected void init() {

        CommonAdapter updateAdapter = new CommonAdapter<UpdatePlan>(R.layout.item_update_plan, com.example.myapplication.BR.updatePlan);
        mDataBinding.recyclerViewPlan.setAdapter(updateAdapter);
        mDataBinding.recyclerViewPlan.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getUpdateList().observe(this, new Observer<List<UpdatePlan>>() {
            @Override
            public void onChanged(List<UpdatePlan> list) {
                updateAdapter.onItemDatasChanged(list);
            }
        });
    }

    /**
     * 展示更新计划
     */
    public void showUpdatePlan() {
        if (mDataBinding.recyclerViewPlan.getVisibility() == View.VISIBLE) {
            mDataBinding.recyclerViewPlan.setVisibility(View.GONE);
        } else {
            mDataBinding.recyclerViewPlan.setVisibility(View.VISIBLE);
        }

    }
}
