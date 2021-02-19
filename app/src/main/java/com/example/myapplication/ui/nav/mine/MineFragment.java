package com.example.myapplication.ui.nav.mine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.config.Constants;
import com.example.myapplication.databinding.FragmentMineBinding;
import com.example.myapplication.bean.responsebean.LoginBean;
import com.example.myapplication.ui.activity.main.MainViewModel;
import com.example.myapplication.util.ImageSelectUtil;
import com.example.myapplication.util.ToastUtils;
import com.guoxiaoxing.phoenix.core.model.MediaEntity;
import com.guoxiaoxing.phoenix.picker.Phoenix;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import static android.app.Activity.RESULT_OK;

/**
 * @author devel
 */
public class MineFragment extends BaseFragment<FragmentMineBinding, MainViewModel> {


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initViewModel() {
        mViewModel = ViewModelProviders.of(getActivity()).get(MainViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);
        mDataBinding.setMineFragment(this);

    }

    @Override
    protected void init() {
        mViewModel.loadMyIntegral();

        initData();
    }

    private void initData() {
        mViewModel.getUserBean().observe(this, bean -> {
            if (bean != null) {
                mDataBinding.tvUserName.setText(bean.getNickname());
            } else {
                mDataBinding.tvUserName.setText(getResources().getString(R.string.no_login));
                mDataBinding.tvUserIntegral.setText("");
            }
        });
    }

    public void selectImage() {
        ImageSelectUtil.selectPictureWithFragment(this, Constants.RequestCode.SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Constants.RequestCode.SELECT_PICTURE) {
                if (Phoenix.result(data) == null) {
                    Toast.makeText(getContext(), "图片选择失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                MediaEntity mediaEntity = Phoenix.result(data).get(0);

                mViewModel.updateUserHeader(mediaEntity.getFinalPath());
            }
        }
    }

    /**
     * 跳转到我的收藏界面
     */
    public void startCollectFragment() {
        NavHostFragment.findNavController(MineFragment.this).navigate(R.id.nav_collect);
    }

    /**
     * 跳转到我的分享界面
     */
    public void startShareFragment() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.ParamCode.PARAM1, mViewModel.getUserBean().getValue().getId());
        bundle.putString(Constants.ParamCode.PARAM2, mViewModel.getUserHeader().getValue());
        NavHostFragment.findNavController(MineFragment.this).navigate(R.id.nav_share, bundle);
    }

    /**
     * 跳转到待办清单界面
     */
    public void startTodFragment() {
        NavHostFragment.findNavController(MineFragment.this).navigate(R.id.toDoFragment);
    }

    /**
     * 跳转积分详情界面
     */
    public void startIntegralFragment() {
        NavHostFragment.findNavController(MineFragment.this).navigate(R.id.coinListFragment);
    }

    /**
     * 跳转积分排行榜
     */
    public void startCoinRankFragment() {
        NavHostFragment.findNavController(MineFragment.this).navigate(R.id.coinRankListFragment);
    }

    /**
     * 跳转设置界面
     */
    public void startSettingFragment() {
        ToastUtils.showToast(getContext(),"功能正在开发中");
//        NavHostFragment.findNavController(MineFragment.this).navigate(R.id.coinRankListFragment);
    }

}
