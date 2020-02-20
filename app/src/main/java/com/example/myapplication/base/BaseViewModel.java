package com.example.myapplication.base;


import com.example.myapplication.enums.LoadState;

import androidx.databinding.ObservableField;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.ViewModel;

/**
 * ViewModel的基类
 */
public abstract class BaseViewModel extends ViewModel implements DefaultLifecycleObserver {

    public final ObservableField<LoadState> loadState = new ObservableField<>();


    /**
     * 获取加载状态
     *
     * @return 加载状态
     */
    public LoadState getLoadState() {
        return loadState.get();
    }


    /**
     * 重新加载数据。没有网络，点击重试时回调
     */
    public void reloadData() {

    }
}
