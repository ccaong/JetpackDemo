package com.example.myapplication.ui.bottom.navi;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.config.LoadState;
import com.example.myapplication.bean.responsebean.NavigationBean;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;
import com.example.myapplication.util.NetworkUtils;

import java.util.List;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author devel
 */
public class NaviViewModel extends BaseViewModel {

    public MutableLiveData<List<NavigationBean>> dataList;

    /**
     * 标题
     */
    public final ObservableList<String> mTitlesList = new ObservableArrayList<>();

    public NaviViewModel() {
        dataList = new MutableLiveData<>();
    }

    public LiveData<List<NavigationBean>> getDataList() {
        return dataList;
    }

    public void loadNavigationData() {
        //判断网络
        if (!NetworkUtils.isConnected()) {
            loadState.postValue(LoadState.NO_NETWORK);
            return;
        }
        loadState.postValue(LoadState.LOADING);
        HttpRequest.getInstance()
                .getNavigationBean()
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<List<NavigationBean>>() {
                    @Override
                    public void success(List<NavigationBean> listBean) {
                        if (!CommonUtils.isListEmpty(listBean)) {
                            loadState.postValue(LoadState.SUCCESS);
                            for (NavigationBean baan : listBean) {
                                mTitlesList.add(baan.getName());
                            }
                            dataList.postValue(listBean);
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
