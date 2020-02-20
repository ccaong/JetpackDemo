package com.example.myapplication.ui.gallery;

import com.example.myapplication.http.HttpDisposable;
import com.example.myapplication.http.HttpFactory;
import com.example.myapplication.http.HttpRequest;
import com.example.myapplication.requestbean.Data;
import com.example.myapplication.requestbean.ImageBean;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


/**
 * @author devel
 */
public class GalleryViewModel extends ViewModel {

    private MutableLiveData<Data<ImageBean.ImagesBean>> mImage;
    private int idx;

    public GalleryViewModel() {
        mImage = new MutableLiveData<>();
        idx = 0;
    }

    public MutableLiveData<Data<ImageBean.ImagesBean>> getImage() {
        return mImage;
    }

    public void loadImage() {
        HttpRequest.getInstance().
                getImage("js", idx, 1)
                .compose(HttpFactory.<ImageBean>schedulers())
                .subscribe(new HttpDisposable<ImageBean>() {
                    @Override
                    public void success(ImageBean imageBean) {
                        mImage.setValue(new Data<ImageBean.ImagesBean>(
                                imageBean.getImages().get(0), null
                        ));
                    }

                    @Override
                    public void onError(Throwable e) {
                        mImage.setValue(new Data<ImageBean.ImagesBean>(
                                null, e.getMessage()
                        ));
                        idx--;
                    }
                });
    }

    public void nextImage() {
        ++idx;
        loadImage();
    }

    public void previousImage() {
        if (idx <= 0) {
            mImage.setValue(new Data<ImageBean.ImagesBean>(
                    null, "已经是第一个了"
            ));
            return;
        }
        --idx;
        loadImage();
    }
}