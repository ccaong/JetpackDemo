package com.example.myapplication.ui.nav.mine.coinrank;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.config.LoadState;
import com.example.myapplication.bean.responsebean.CoinRankBean;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;
import com.example.myapplication.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author : devel
 * @date : 2020/2/26 16:28
 * @desc :
 */
public class CoinRankViewModel extends BaseViewModel {

    private MutableLiveData<CoinRankBean> mCoinList;
    private List<CoinRankBean.RankDataBean> mList;

    private int mPage;

    public CoinRankViewModel() {
        mPage = 1;
        mList = new ArrayList<>();
        mCoinList = new MutableLiveData<>();
    }


    public LiveData<CoinRankBean> getCoinList() {
        return mCoinList;
    }

    /**
     * 刷新
     */
    public void refreshData(Boolean refresh) {
        if (refresh) {
            mPage = 1;
        } else {
            mPage++;
        }
        mRefresh = true;
        queryCoinRankList();
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
        mPage = 1;
        mRefresh = false;
        queryCoinRankList();
    }

    /**
     * 获取积分列表详情
     */
    private void queryCoinRankList() {
        //判断网络
        if (!NetworkUtils.isConnected()) {
            loadState.postValue(LoadState.NO_NETWORK);
            return;
        }
        HttpRequest.getInstance()
                .queryCoinRank(mPage)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<CoinRankBean>() {
                    @Override
                    public void success(CoinRankBean bean) {

                        if (CommonUtils.isListEmpty(bean.getDatas())) {
                            loadState.postValue(LoadState.NO_DATA);
                            return;
                        }

                        loadState.postValue(LoadState.SUCCESS);
                        if (mPage == 0) {
                            //第一次加载或刷新成功 清空列表，重新载入数据，设置刷新成功状态
                            mList.clear();
                            mList.addAll(bean.getDatas());
                            mCoinList.postValue(bean);
                        } else {
                            //添加数据，设置下拉加载成功状态
                            mList.addAll(bean.getDatas());
                            bean.setDatas(mList);
                            mCoinList.postValue(bean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadState.postValue(LoadState.ERROR);
                    }
                });
    }
}
