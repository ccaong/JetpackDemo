package com.example.myapplication.ui.activity.splash;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.databinding.ActivitySplashBinding;
import com.example.myapplication.entity.livedata.ActivitySkip;
import com.example.myapplication.http.bean.ImageBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.ui.activity.main.MainActivity;
import com.example.myapplication.ui.activity.web.DetailsActivity;
import com.example.myapplication.util.CommonUtils;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * @author : devel
 * @date : 2020/2/21 17:00
 * @desc : 闪屏页
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {

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
        mViewModel.getImageData().observe(this, new Observer<HttpBaseResponse<ImageBean>>() {
            @Override
            public void onChanged(HttpBaseResponse<ImageBean> imageBean) {

                if (imageBean.errorCode == 0) {
                    Glide.with(SplashActivity.this)
                            .load(imageBean.data.getImages().get(0).getBaseUrl()
                                    + imageBean.data.getImages().get(0).getUrl())
                            .into(mDataBinding.ivSplash);
                } else {
                    //从网络获取图片失败，加载本地默认图片
                    Glide.with(SplashActivity.this)
                            .load(R.mipmap.splash_bg)
                            .into(mDataBinding.ivSplash);
                }
            }
        });

        //跳转到指定的界面
        mViewModel.getActivitySkip().observe(this, new Observer<ActivitySkip>() {
            @Override
            public void onChanged(ActivitySkip activitySkip) {
                if ("DetailsActivity".equals(activitySkip.getmActivity())) {
                    if (!CommonUtils.isStringEmpty(activitySkip.getParam1())) {
                        DetailsActivity.start(SplashActivity.this,
                                activitySkip.getParam1(), true);
                        finish();
                    } else {
                        MainActivity.start(SplashActivity.this,false);
                        finish();
                    }
                } else if ("MainActivity".equals(activitySkip.getmActivity())) {
                    MainActivity.start(SplashActivity.this,false);
                    finish();
                }
            }
        });
    }
}
