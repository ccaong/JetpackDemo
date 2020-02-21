package com.example.myapplication.ui.activity;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.entity.ImageBean;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.schedulers.Schedulers;

/**
 * @author : devel
 * @date : 2020/2/21 17:08
 * @desc :
 */
public class SplashViewModel extends BaseViewModel {

    private MutableLiveData<ImageBean> mImage;

    public SplashViewModel() {
        mImage = new MutableLiveData<>();
    }


    public LiveData<ImageBean> getImageData() {
        return mImage;
    }

    /**
     * 获取Bing图片
     */
    public void loadImageView() {

        HttpRequest.getInstance("https://www.bing.com/")
                .getImage("js", 0, 1)
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<ImageBean>() {
                    @Override
                    public void success(ImageBean imageBean) {
                        mImage.postValue(imageBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });


    }

}
