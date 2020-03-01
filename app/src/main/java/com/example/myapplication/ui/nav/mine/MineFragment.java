package com.example.myapplication.ui.nav.mine;

import android.content.Intent;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.base.BaseFragment;
import com.example.myapplication.common.Code;
import com.example.myapplication.databinding.FragmentMineBinding;
import com.example.myapplication.http.bean.LoginBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.ui.activity.main.MainViewModel;
import com.example.myapplication.util.ImageSelectUtil;
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
        mViewModel.getUserBean().observe(this, new Observer<LoginBean>() {
            @Override
            public void onChanged(LoginBean bean) {
                if (bean != null) {
                    mDataBinding.tvUserName.setText(bean.getNickname());
                } else {
                    mDataBinding.tvUserName.setText(getResources().getString(R.string.no_login));
                    mDataBinding.tvUserIntegral.setText("");
                }
            }
        });
    }

    public void selectImage() {
        ImageSelectUtil.selectPictureWithFragment(this, Code.RequestCoode.SELECT_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == Code.RequestCoode.SELECT_PICTURE) {
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

}
