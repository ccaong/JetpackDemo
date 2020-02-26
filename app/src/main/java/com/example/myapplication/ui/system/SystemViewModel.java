package com.example.myapplication.ui.system;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.http.bean.System;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.NetworkUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

/**
 * @author devel
 */
public class SystemViewModel extends BaseViewModel {

    private MutableLiveData<List<System>> mSystemList;


    public SystemViewModel() {
        mSystemList = new MutableLiveData<>();

    }


    public LiveData<List<System>> getSystemList() {
        return mSystemList;
    }

    /**
     * 刷新
     */
    public void refreshData() {
        querySystemList();
    }

    /**
     * 重新加载
     */
    @Override
    public void reloadData() {
        loadData();
    }

    /**
     * 第一次加载数据
     */
    public void loadData() {
        loadState.postValue(LoadState.LOADING);
        querySystemList();
    }

    /**
     * 获取体系列表详情
     */
    private void querySystemList() {
        //判断网络
        if (!NetworkUtils.isConnected()) {
            loadState.postValue(LoadState.NO_NETWORK);
            return;
        }
        HttpRequest.getInstance()
                .getSystemList()
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<List<System>>>() {
                    @Override
                    public void success(HttpBaseResponse<List<System>> bean) {

                        if (bean != null && bean.errorCode == 0) {
                            loadState.postValue(LoadState.SUCCESS);
                            mSystemList.postValue(bean.data);
                        } else {
                            loadState.postValue(LoadState.NO_DATA);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadState.postValue(LoadState.ERROR);
                    }
                });
    }

}