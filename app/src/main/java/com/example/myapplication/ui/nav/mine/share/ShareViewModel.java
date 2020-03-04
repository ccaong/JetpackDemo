package com.example.myapplication.ui.nav.mine.share;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.config.LoadState;
import com.example.myapplication.bean.responsebean.ArticleBean;
import com.example.myapplication.bean.responsebean.ArticleListBean;
import com.example.myapplication.bean.responsebean.UserShareArticleBean;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class ShareViewModel extends BaseViewModel {

    /**
     * 登陆用户信息
     */
    public MutableLiveData<UserShareArticleBean.CoinInfoBean> userInfo;

    /**
     * 用户头像
     */
    public MutableLiveData<String> userHeader;

    /**
     * 分享的文章信息
     */
    private MutableLiveData<ArticleListBean> mArticleList;
    private List<ArticleBean> mList;
    private int mPage = 0;
    private int userId;


    public ShareViewModel() {
        userInfo = new MutableLiveData<>();
        userHeader = new MutableLiveData<>();

        mArticleList = new MutableLiveData<>();
        mList = new ArrayList<>();
    }

    public void setUserHeader(String path) {
        userHeader.postValue(path);
    }

    public void setId(int id) {
        this.userId = id;
    }

    public LiveData<ArticleListBean> getArticleList() {
        return mArticleList;
    }

    /**
     * 刷新
     */
    public void refreshData(Boolean refresh) {
        if (refresh) {
            mPage = 0;
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

        mPage = 0;
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
     * 加载分享数据
     */
    private void loadWeChatArticleList() {
        HttpRequest.getInstance()
                .getUserShareArticle(userId, mPage)
                .compose(HttpFactory.<UserShareArticleBean>schedulers())
                .subscribe(new HttpDisposable<UserShareArticleBean>() {
                    @Override
                    public void success(UserShareArticleBean mArticleListBean) {

                        if (mArticleListBean != null) {
                            loadState.postValue(LoadState.SUCCESS);

                            if (mPage == 0) {
                                //第一次加载或刷新成功
                                userInfo.postValue(mArticleListBean.getCoinInfo());

                                mList.clear();
                                mList.addAll(mArticleListBean.getShareArticles().getDatas());
                                mArticleList.postValue(mArticleListBean.getShareArticles());


                            } else {
                                //下拉加载更多成功
                                //添加数据，设置下拉加载成功状态
                                mList.addAll(mArticleListBean.getShareArticles().getDatas());
                                mArticleListBean.getShareArticles().setDatas(mList);
                                mArticleList.postValue(mArticleListBean.getShareArticles());
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