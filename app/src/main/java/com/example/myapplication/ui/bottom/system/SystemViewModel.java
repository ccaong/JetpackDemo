package com.example.myapplication.ui.bottom.system;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.config.LoadState;
import com.example.myapplication.bean.responsebean.WeChatBean;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;
import com.example.myapplication.util.NetworkUtils;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<List<WeChatBean>>() {
                    @Override
                    public void success(List<WeChatBean> bean) {
                        if (!CommonUtils.isListEmpty(bean)) {
                            loadState.postValue(LoadState.SUCCESS);
                            mSystemList.postValue(bean);
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