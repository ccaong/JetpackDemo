package com.example.myapplication.ui.bottom.wechat;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.config.LoadState;
import com.example.myapplication.bean.responsebean.WeChatBean;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;
import com.example.myapplication.util.NetworkUtils;

import java.util.List;

import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableList;

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
        if (!NetworkUtils.isConnected()) {
            loadState.postValue(LoadState.NO_NETWORK);
            return;
        }
        loadState.postValue(LoadState.LOADING);
        HttpRequest.getInstance()
                .getWechatList()
                .compose(HttpFactory.<List<WeChatBean>>schedulers())
                .subscribe(new HttpDisposable<List<WeChatBean>>() {
                    @Override
                    public void success(List<WeChatBean> listHttpBaseResponse) {
                        if (!CommonUtils.isListEmpty(listHttpBaseResponse)) {
                            dataList.addAll(listHttpBaseResponse);
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