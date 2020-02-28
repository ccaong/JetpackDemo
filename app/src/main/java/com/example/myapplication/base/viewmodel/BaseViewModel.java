package com.example.myapplication.base.viewmodel;


import android.content.res.Resources;
import android.util.Log;

import com.example.myapplication.App;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.enums.RefreshState;
import com.example.myapplication.http.bean.ArticleListBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;

import java.util.Optional;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.schedulers.Schedulers;

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
    public MutableLiveData<HttpBaseResponse<Object>> collect = new MutableLiveData<>();

    /**
     * 刷新状态
     */
    public MutableLiveData<RefreshState> refreshState = new MutableLiveData<>();

    /**
     * 加载状态
     */
    public MutableLiveData<LoadState> loadState = new MutableLiveData<>();

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

    public LiveData<HttpBaseResponse<Object>> getCollectStatus() {
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
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<Object>>() {
                    @Override
                    public void success(HttpBaseResponse<Object> mArticleListBean) {
                        collect.postValue(mArticleListBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
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
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<Object>>() {
                    @Override
                    public void success(HttpBaseResponse<Object> mArticleListBean) {
                        collect.postValue(mArticleListBean);
                    }
                });
    }
}
