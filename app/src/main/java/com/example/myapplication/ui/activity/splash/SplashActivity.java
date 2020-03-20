package com.example.myapplication.ui.activity.splash;

import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.myapplication.config.App;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.config.Constants;
import com.example.myapplication.databinding.ActivitySplashBinding;
import com.example.myapplication.bean.ActivitySkip;
import com.example.myapplication.bean.responsebean.ImageBean;
import com.example.myapplication.ui.activity.main.MainActivity;
import com.example.myapplication.ui.activity.web.DetailsActivity;
import com.example.myapplication.util.CommonUtils;
import com.orhanobut.hawk.Hawk;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author : devel
 * @date : 2020/2/21 17:00
 * @desc : 闪屏页
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        App.firstOpen = true;
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isNoActionBar() {
        //沉浸式主题
        return true;
    }

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
        //加载闪屏页图片
        mViewModel.getImageData().observe(this, imageBean -> {
            if (imageBean != null) {
                String url = imageBean.getImages().get(0).getBaseUrl()
                        + imageBean.getImages().get(0).getUrl();

                Glide.with(SplashActivity.this)
                        .load(url)
                        .into(mDataBinding.ivSplash);

                Hawk.put(Constants.HawkCode.SPLASH_IMAGE_URL, url);
            } else {
                //从网络获取图片失败，加载本地默认图片
                Glide.with(SplashActivity.this)
                        .load(R.mipmap.splash_bg)
                        .into(mDataBinding.ivSplash);
            }
        });

        //跳转到指定的界面
        mViewModel.getActivitySkip().observe(this, activitySkip -> {
            if ("DetailsActivity".equals(activitySkip.getmActivity())) {
                if (!CommonUtils.isStringEmpty(activitySkip.getParam1())) {
                    DetailsActivity.start(SplashActivity.this,
                            activitySkip.getParam1(), true);
                    finish();
                } else {
                    MainActivity.start(SplashActivity.this, false);
                    finish();
                }
            } else if ("MainActivity".equals(activitySkip.getmActivity())) {
                MainActivity.start(SplashActivity.this, false);
                finish();
            }
        });
    }
}
