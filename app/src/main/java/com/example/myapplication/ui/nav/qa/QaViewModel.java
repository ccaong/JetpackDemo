package com.example.myapplication.ui.nav.qa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.bean.responsebean.ArticleBean;
import com.example.myapplication.bean.responsebean.ArticleListBean;
import com.example.myapplication.config.LoadState;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

public class QaViewModel extends BaseViewModel {

    private MutableLiveData<ArticleListBean> mArticleList;
    private List<ArticleBean> mList;
    private int mPage = 1;

    public QaViewModel() {
        mArticleList = new MediatorLiveData<>();
        mList = new ArrayList<>();
    }

    public LiveData<ArticleListBean> getArticleList() {
        return mArticleList;
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
        loadArticleList();
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
        loadArticleList();
    }


    /**
     * 加载文章列表
     */
    private void loadArticleList() {
        //判断网络
        if (!NetworkUtils.isConnected()) {
            loadState.postValue(LoadState.NO_NETWORK);
            return;
        }
        loadWeChatArticleList();
    }

    /**
     * 加载微信公众号数据
     */
    private void loadWeChatArticleList() {
        HttpRequest.getInstance()
                .queryQaArtistList(mPage)
                .compose(HttpFactory.<ArticleListBean>schedulers())
                .subscribe(new HttpDisposable<ArticleListBean>() {
                    @Override
                    public void success(ArticleListBean mArticleListBean) {

                        if (mArticleListBean != null) {
                            loadState.postValue(LoadState.SUCCESS);

                            if (mPage == 1) {
                                //第一次加载或刷新成功
                                //清空列表，重新载入数据，设置刷新成功状态
                                mList.clear();
                                mList.addAll(mArticleListBean.getDatas());
                                mArticleList.postValue(mArticleListBean);

                            } else {
                                //下拉加载更多成功
                                //添加数据，设置下拉加载成功状态
                                mList.addAll(mArticleListBean.getDatas());
                                mArticleListBean.setDatas(mList);
                                mArticleList.postValue(mArticleListBean);
                            }
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
