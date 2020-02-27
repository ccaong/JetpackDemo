package com.example.myapplication.base.viewmodel;


import android.content.res.Resources;

import com.example.myapplication.App;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.enums.RefreshState;

import androidx.lifecycle.DefaultLifecycleObserver;
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
     * 收藏文章
     */
    public void collectArticle(boolean collect) {

    }
}
