package com.example.myapplication.ui.bottom.system;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.http.bean.WeChatBean;
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

    private MutableLiveData<List<WeChatBean>> mSystemList;


    public SystemViewModel() {
        mSystemList = new MutableLiveData<>();
    }

    public LiveData<List<WeChatBean>> getSystemList() {
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
                .subscribe(new HttpDisposable<HttpBaseResponse<List<WeChatBean>>>() {
                    @Override
                    public void success(HttpBaseResponse<List<WeChatBean>> bean) {

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