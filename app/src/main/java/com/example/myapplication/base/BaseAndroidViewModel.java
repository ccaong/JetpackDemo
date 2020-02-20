package com.example.myapplication.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.enums.LoadState;

public abstract class BaseAndroidViewModel extends AndroidViewModel implements DefaultLifecycleObserver {

    /**
     * 加载状态
     */
    public MutableLiveData<LoadState> loadState = new MutableLiveData<>();


    public BaseAndroidViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 重新加载数据。没有网络，点击重试时回调
     */
    public void reloadData() {

    }
}
