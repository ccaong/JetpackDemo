package com.example.myapplication.ui.wechat;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.App;
import com.example.myapplication.ThreadManager;
import com.example.myapplication.base.BaseViewModel;
import com.example.myapplication.entity.WeChatListEntity;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.room.AppDataBase;
import com.example.myapplication.room.dao.WeChatDao;
import com.example.myapplication.util.NetworkUtils;

import io.reactivex.schedulers.Schedulers;

public class WeChatViewModel extends BaseViewModel {

    private MutableLiveData<WeChatListEntity> mWeChatList;
    private ThreadManager.ThreadPool threadPool;
    private WeChatDao weChatDao;


    public WeChatViewModel() {
        mWeChatList = new MutableLiveData<>();
        threadPool = ThreadManager.getThreadPool();
        weChatDao = AppDataBase.getInstance(App.getContext()).getWeChatDao();
    }


    public LiveData<WeChatListEntity> getWeChatList() {
        return mWeChatList;
    }

    /**
     * 获取微信公众号列表
     */
    public void loadWeChatList() {

        if (NetworkUtils.getWifiEnabled()) {
            loadDataByNetWork();
        } else {
            loadDataByDataBase();
        }
    }

    /**
     * 从网络接口获取数据
     */
    private void loadDataByNetWork() {
        HttpRequest.getInstance()
                .getWechatList()
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<WeChatListEntity>() {
                    @Override
                    public void success(WeChatListEntity wechatListEntity) {

//                        threadPool.execute(() -> weChatDao.deleteAll());
//                        threadPool.execute(() -> weChatDao.insertList(wechatListEntity.getData()));

                        mWeChatList.postValue(wechatListEntity);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    WeChatListEntity wechatListEntity;

    private void loadDataByDataBase() {
        wechatListEntity = new WeChatListEntity();
        wechatListEntity.setErrorMsg("");
        wechatListEntity.setErrorCode(0);
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                wechatListEntity.setData(weChatDao.getAll());
                mWeChatList.postValue(wechatListEntity);
            }
        });
    }


}