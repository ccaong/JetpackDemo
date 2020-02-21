package com.example.myapplication.ui.wechat;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.entity.ArticleBean;
import com.example.myapplication.entity.ArticleListBean;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.schedulers.Schedulers;

/**
 * @author devel
 */
public class WeChatContentListViewModel extends BaseViewModel {

    private int mPage = 0;
    private int mId = 0;
    private MutableLiveData<ArticleListBean> mArticleList;
    private List<ArticleBean> mList;

    public WeChatContentListViewModel() {

        mArticleList = new MediatorLiveData<>();
        mList = new ArrayList<>();
    }

    public void setId(int id) {
        this.mId = id;
    }

    public LiveData<ArticleListBean> getArticleList() {
        return mArticleList;
    }

    /**
     * 加载更多
     */
    public void loadMoreData() {
        mPage++;
        loadData();
    }

    /**
     * 刷新
     */
    public void refreshData() {
        mPage = 0;
        loadData();
    }

    /**
     * 重新加载
     */
    @Override
    public void reloadData() {
        mPage = 0;
        loadData();
    }

    /**
     * 加载数据
     */
    public void loadData() {

        //判断网络
        if (!NetworkUtils.isConnected()) {
            loadState.postValue(LoadState.NO_NETWORK);
            return;
        }

        if (!mRefresh) {
            loadState.postValue(LoadState.LOADING);
        }

        HttpRequest.getInstance()
                .getWechatArticleList(mId, mPage)
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<ArticleListBean>>() {
                    @Override
                    public void success(HttpBaseResponse<ArticleListBean> mArticleListBean) {

                        if (mArticleListBean != null && mArticleListBean.errorCode == 0) {
                            if (!mRefresh) {
                                loadState.postValue(LoadState.SUCCESS);
                            }

                            if (mPage == 0) {
                                mList.clear();
                                mList.addAll(mArticleListBean.data.getDatas());
                                mArticleList.postValue(mArticleListBean.data);
                            } else {
                                mList.addAll(mArticleListBean.data.getDatas());
                                mArticleListBean.data.setDatas(mList);
                                mArticleList.postValue(mArticleListBean.data);
                            }
                        } else {
                            loadState.postValue(LoadState.ERROR);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadState.postValue(LoadState.ERROR);
                    }
                });
    }


}
