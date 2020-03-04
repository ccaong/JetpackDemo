package com.example.myapplication.ui.bottom.project;

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
public class ProjectViewModel extends BaseViewModel {

    public MutableLiveData<List<WeChatBean>> dataList;

    public ProjectViewModel() {
        dataList = new MutableLiveData<>();
    }


    public LiveData<List<WeChatBean>> getDataList() {
        return dataList;
    }

    /**
     * 获取项目分类列表
     */
    public void loadProject() {
        //判断网络
        if (!NetworkUtils.isConnected()) {
            loadState.postValue(LoadState.NO_NETWORK);
            return;
        }
        loadState.postValue(LoadState.LOADING);
        HttpRequest.getInstance()
                .getProjectListData()
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<List<WeChatBean>>() {
                    @Override
                    public void success(List<WeChatBean> listHttpBaseResponse) {
                        if (!CommonUtils.isListEmpty(listHttpBaseResponse)) {
                            dataList.postValue(listHttpBaseResponse);
                            loadState.postValue(LoadState.SUCCESS);
                        } else {
                            loadState.postValue(LoadState.NO_DATA);
                        }
                    }
                });
    }
}
