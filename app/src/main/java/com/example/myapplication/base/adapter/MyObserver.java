package com.example.myapplication.base.adapter;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

/**
 * @author : devel
 * @date : 2020/2/24 16:54
 * @desc :
 */

public class MyObserver implements LifecycleObserver {
    private static final String TAG="MyObserver";

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    public void onCreated(){
        Log.d(TAG, "onCreated: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void onStart(){
        Log.d(TAG, "onStart: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void onResume(){
        Log.d(TAG, "onResume: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    public void onPause(){
        Log.d(TAG, "onPause: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void onStop(){
        Log.d(TAG, "onStop: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    public void customMethod(){
        Log.d(TAG, "customMethod: ");
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    public void onAny(){//此方法可以有参数，但类型必须如两者之一(LifecycleOwner owner,Lifecycle.Event event)
        Log.d(TAG, "onAny: ");
    }
}
