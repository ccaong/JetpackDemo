package com.example.myapplication.ui.bottom.navi;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.http.bean.NavigationBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;

import java.util.List;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.schedulers.Schedulers;

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

        loadState.postValue(LoadState.LOADING);
        HttpRequest.getInstance()
                .getNavigationBean()
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<List<NavigationBean>>>() {
                    @Override
                    public void success(HttpBaseResponse<List<NavigationBean>> listBean) {
                        if (!CommonUtils.isListEmpty(listBean.data)) {
                            loadState.postValue(LoadState.SUCCESS);
                            for (NavigationBean baan : listBean.data) {
                                mTitlesList.add(baan.getName());
                            }
                            dataList.postValue(listBean.data);
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
