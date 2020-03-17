package com.example.myapplication.ui.nav.update;

import android.view.View;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.databinding.UpdateFragmentBinding;
import com.example.myapplication.bean.UpdatePlan;
import com.example.myapplication.ui.activity.web.DetailsActivity;
import com.example.myapplication.ui.adapter.CommonAdapter;

import java.util.List;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

public class UpdateFragment extends BaseFragment<UpdateFragmentBinding, UpdateViewModel> {

    @Override
    protected int getLayoutResId() {
        return R.layout.update_fragment;
    }

    @Override
    protected void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(UpdateViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
        mDataBinding.setFragment(UpdateFragment.this);
    }

    @Override
    protected void init() {

        CommonAdapter updateAdapter = new CommonAdapter<UpdatePlan>(R.layout.item_update_plan, com.example.myapplication.BR.updatePlan);
        mDataBinding.recyclerViewPlan.setAdapter(updateAdapter);
        mDataBinding.recyclerViewPlan.setLayoutManager(new LinearLayoutManager(getContext()));

        mViewModel.getUpdateList().observe(this, list -> updateAdapter.onItemDatasChanged(list));
    }

    /**
     * 展示更新计划
     */
    public void showUpdatePlan() {
        if (mDataBinding.recyclerViewPlan.getVisibility() == View.VISIBLE) {
            mDataBinding.recyclerViewPlan.setVisibility(View.GONE);
            mDataBinding.ivPlan.setImageResource(R.mipmap.ic_chevron_down);
        } else {
            mDataBinding.recyclerViewPlan.setVisibility(View.VISIBLE);
            mDataBinding.ivPlan.setImageResource(R.mipmap.ic_chevron_up);
        }
    }

    /**
     * 展示更新计划
     */
    public void showUpdateRecord() {
        if (mDataBinding.llRecord.getVisibility() == View.VISIBLE) {
            mDataBinding.llRecord.setVisibility(View.GONE);
            mDataBinding.ivRecord.setImageResource(R.mipmap.ic_chevron_down);
        } else {
            mDataBinding.llRecord.setVisibility(View.VISIBLE);
            mDataBinding.ivRecord.setImageResource(R.mipmap.ic_chevron_up);
        }
    }

    public void startProject(){
        DetailsActivity.start(getContext(),"https://github.com/ccaong/JetpackDemo");
    }
}
