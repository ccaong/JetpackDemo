package com.example.myapplication.ui.activity;

import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.databinding.ActivitySplashBinding;
import com.example.myapplication.entity.ImageBean;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author : devel
 * @date : 2020/2/21 17:00
 * @desc :
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initViewModel() {

        mViewModel = ViewModelProviders.of(this).get(SplashViewModel.class);
    }

    @Override
    protected void bindViewModel() {
        mDataBinding.setViewModel(mViewModel);

    }

    @Override
    protected void init() {

        mViewModel.loadImageView();

        mViewModel.getImageData().observe(this, new Observer<ImageBean>() {
            @Override
            public void onChanged(ImageBean imageBean) {
                if (imageBean.getImages() != null) {
                    Glide.with(SplashActivity.this)
                            .load(imageBean.getImages().get(0).getBaseUrl() + imageBean.getImages().get(0).getUrl())
                            .into(mDataBinding.ivSplash);
                }
            }
        });
    }
}
