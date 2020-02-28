package com.example.myapplication.ui.bottom.project;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.http.bean.WeChatBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.schedulers.Schedulers;

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

        loadState.postValue(LoadState.LOADING);
        HttpRequest.getInstance()
                .getProjectListData()
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<List<WeChatBean>>>() {
                    @Override
                    public void success(HttpBaseResponse<List<WeChatBean>> listHttpBaseResponse) {
                        if (!CommonUtils.isListEmpty(listHttpBaseResponse.data)) {
                            dataList.postValue(listHttpBaseResponse.data);
                            loadState.postValue(LoadState.SUCCESS);
                        } else {
                            loadState.postValue(LoadState.NO_DATA);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }
}
