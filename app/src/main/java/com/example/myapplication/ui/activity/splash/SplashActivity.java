package com.example.myapplication.ui.activity.splash;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bumptech.glide.Glide;
import com.example.myapplication.ui.activity.main.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.base.BaseActivity;
import com.example.myapplication.databinding.ActivitySplashBinding;
import com.example.myapplication.entity.livedata.ActivitySkip;
import com.example.myapplication.http.bean.ImageBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.ui.activity.web.DetailsActivity;
import com.example.myapplication.util.CommonUtils;

/**
 * @author : devel
 * @date : 2020/2/21 17:00
 * @desc :
 */
public class SplashActivity extends BaseActivity<ActivitySplashBinding, SplashViewModel> {

    @Override
    protected boolean isNoActionBar() {
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

        mViewModel.getImageData().observe(this, new Observer<HttpBaseResponse<ImageBean>>() {
            @Override
            public void onChanged(HttpBaseResponse<ImageBean> imageBean) {

                if (imageBean.errorCode == 0) {
                    Glide.with(SplashActivity.this)
                            .load(imageBean.data.getImages().get(0).getBaseUrl()
                                    + imageBean.data.getImages().get(0).getUrl())
                            .into(mDataBinding.ivSplash);
                } else {
                    Glide.with(SplashActivity.this)
                            .load(R.mipmap.splash_bg)
                            .into(mDataBinding.ivSplash);
                }
            }
        });

        mViewModel.getActivitySkip().observe(this, new Observer<ActivitySkip>() {
            @Override
            public void onChanged(ActivitySkip activitySkip) {
                if ("DetailsActivity".equals(activitySkip.getmActivity())) {
                    if (!CommonUtils.isStringEmpty(activitySkip.getParam1())) {

                        DetailsActivity.start(SplashActivity.this,
                                activitySkip.getParam1(), activitySkip.getParam2());
                        finish();
                    } else {
                        MainActivity.start(SplashActivity.this);
                        finish();
                    }
                } else if ("MainActivity".equals(activitySkip.getmActivity())) {
                    MainActivity.start(SplashActivity.this);
                    finish();
                }
            }
        });
    }
}
