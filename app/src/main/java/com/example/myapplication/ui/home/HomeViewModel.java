package com.example.myapplication.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entity.HomeBannerEntity;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;

import io.reactivex.schedulers.Schedulers;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<HomeBannerEntity> mBanner;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        mBanner = new MutableLiveData<>();
    }

    public LiveData<HomeBannerEntity> getBanner() {
        return mBanner;
    }


    /**
     * 从网络接口获取数据
     */
    public void loadDataByNetWork() {
        HttpRequest.getInstance()
                .getBanner()
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HomeBannerEntity>() {
                    @Override
                    public void success(HomeBannerEntity homeBannerEntity) {
                        mBanner.postValue(homeBannerEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}