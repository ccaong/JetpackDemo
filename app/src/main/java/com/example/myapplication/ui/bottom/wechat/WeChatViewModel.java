package com.example.myapplication.ui.bottom.wechat;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.http.bean.WeChatBean;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;

import java.util.List;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;
import io.reactivex.schedulers.Schedulers;

/**
 * @author devel
 */
public class WeChatViewModel extends BaseViewModel {

    public ObservableList<WeChatBean> dataList;

    public WeChatViewModel() {
        dataList = new ObservableArrayList<>();
    }

    public void setDataList(List<WeChatBean> mList) {
        dataList.addAll(mList);
    }

    /**
     * 获取微信公众号列表
     */
    public void loadWeChatList() {

        loadState.postValue(LoadState.LOADING);
        HttpRequest.getInstance()
                .getWechatList()
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<List<WeChatBean>>>() {
                    @Override
                    public void success(HttpBaseResponse<List<WeChatBean>> listHttpBaseResponse) {
                        if (!CommonUtils.isListEmpty(listHttpBaseResponse.data)) {
                            dataList.addAll(listHttpBaseResponse.data);
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