package com.example.myapplication.base.viewmodel;


import android.content.res.Resources;

import com.example.myapplication.config.App;
import com.example.myapplication.R;
import com.example.myapplication.config.LoadState;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel的基类
 *
 * @author devel
 */
public abstract class BaseViewModel extends ViewModel implements DefaultLifecycleObserver {

    public Resources resources;

    /**
     * 收藏文章
     */
    public MutableLiveData<Object> collect = new MutableLiveData<>();

    /**
     * 加载状态
     */
    public MutableLiveData<LoadState> loadState = new MutableLiveData<>();

    public MutableLiveData<String> errorMsg = new MutableLiveData<>(getResources().getString(R.string.load_error));

    /**
     * 是否为刷新数据
     */
    public boolean mRefresh;

    /**
     * 重新加载数据。没有网络，点击重试时回调
     */
    public void reloadData() {

    }

    public Resources getResources() {
        if (resources == null) {
            resources = App.getContext().getResources();
        }
        return resources;
    }

    /**
     * 改变文章收藏状态
     */
    public void changeArticleCollect(boolean collect, int id) {

        if (collect) {
            collectArticle(id);
        } else {
            unCollectArticle(id);
        }
    }

    public LiveData<Object> getCollectStatus() {
        return collect;
    }

    /**
     * 收藏文章
     *
     * @param id
     */
    private void collectArticle(int id) {

        HttpRequest.getInstance()
                .collectArticle(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<Object>() {
                    @Override
                    public void success(Object mArticleListBean) {
                        collect.postValue(mArticleListBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        collect.postValue(null);
                    }
                });
    }

    /**
     * 取消收藏文章
     *
     * @param id
     */
    private void unCollectArticle(int id) {
        HttpRequest.getInstance()
                .unCollectArticle(id)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<Object>() {
                    @Override
                    public void success(Object mArticleListBean) {
                        collect.postValue(mArticleListBean);
                    }
                });
    }
}
