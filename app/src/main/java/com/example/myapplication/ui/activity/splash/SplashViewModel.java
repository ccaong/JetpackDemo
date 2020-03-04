package com.example.myapplication.ui.activity.splash;

import com.example.myapplication.R;
import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.bean.ActivitySkip;
import com.example.myapplication.bean.responsebean.ImageBean;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.http.request.ServerAddress;
import com.example.myapplication.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Observable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;

/**
 * @author : devel
 * @date : 2020/2/21 17:08
 * @desc :
 */
public class SplashViewModel extends BaseViewModel {

    /**
     * 每日图片
     */
    private MutableLiveData<ImageBean> mImage;
    /**
     * 闪屏页倒计时
     */
    private MutableLiveData<String> mTimer;

    private MutableLiveData<ActivitySkip> mActivitySkip;

    private String url;

    public SplashViewModel() {
        mActivitySkip = new MutableLiveData();
        mImage = new MutableLiveData<>();
        mTimer = new MutableLiveData<>();
        mTimer.postValue(getResources().getString(R.string.skip) + getResources().getString(R.string.time_5));
    }


    public LiveData<ImageBean> getImageData() {
        return mImage;
    }

    public LiveData<String> getTimer() {
        return mTimer;
    }

    public LiveData<ActivitySkip> getActivitySkip() {
        return mActivitySkip;
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {
        super.onCreate(owner);
        startTimer();
        loadImageView();
    }

    /**
     * 获取Bing每日图片
     */
    public void loadImageView() {

        if (!NetworkUtils.isConnected()) {
            //没有网络连接
            mImage.postValue(null);
        } else {
            HttpRequest.getInstance(ServerAddress.API_BING)
                    .getImage("js", 0, 1)
                    .compose(HttpFactory.schedulers())
                    .subscribe(new HttpDisposable<ImageBean>() {
                        @Override
                        public void success(ImageBean imageBean) {
                            mImage.postValue(imageBean);
                            url = imageBean.getImages().get(0).getCopyrightlink();
                        }

                        @Override
                        public void onError(Throwable e) {
                            mImage.postValue(null);
                        }
                    });
        }

    }

    /**
     * 开始倒计时
     */
    public void startTimer() {
        List<String> list = new ArrayList<>();
        for (int i = 4; i >= 0; i--) {
            list.add(i + getResources().getString(R.string.time_s));
        }

        Observable<String> observable = Observable.fromIterable(list);
        Observable<Long> time = Observable.interval(1, TimeUnit.SECONDS);
        Observable.zip(observable, time, new BiFunction<String, Long, String>() {
            @Override
            public String apply(String s, Long aLong) throws Exception {
                return s;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String str) throws Exception {
                if ((getResources().getString(R.string.time_0)).equals(str)) {
                    startMainActivity();
                }
                mTimer.postValue(getResources().getString(R.string.skip) + str);
            }
        });
    }


    /**
     * 跳转到每日图片详情界面
     */
    public void startSplashImageDetail() {
        ActivitySkip skip = new ActivitySkip();
        skip.setmActivity("DetailsActivity");
        skip.setParam1(url);
        mActivitySkip.postValue(skip);
    }

    /**
     * 跳转到主页
     */
    public void startMainActivity() {
        ActivitySkip skip = new ActivitySkip();
        skip.setmActivity("MainActivity");
        mActivitySkip.postValue(skip);
    }

}
