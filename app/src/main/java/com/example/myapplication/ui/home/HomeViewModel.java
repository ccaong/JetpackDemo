package com.example.myapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.http.bean.ArticleBean;
import com.example.myapplication.http.bean.ArticleListBean;
import com.example.myapplication.http.bean.HomeBannerEntity;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.enums.RefreshState;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.schedulers.Schedulers;

/**
 * @author devel
 */
public class HomeViewModel extends BaseViewModel {

    private MutableLiveData<HomeBannerEntity> mBanner;
    private MutableLiveData<ArticleListBean> mArticleList;

    public MutableLiveData<RefreshState> mRefreshState;

    private List<ArticleBean> mList;

    /**
     * 请求页码
     */
    private int mPageNum;

    public HomeViewModel() {
        mBanner = new MutableLiveData<>();
        mArticleList = new MutableLiveData<>();
        mRefreshState = new MutableLiveData<>();
        mList = new ArrayList<>();
    }

    public LiveData<HomeBannerEntity> getBanner() {
        return mBanner;
    }

    public LiveData<ArticleListBean> getArticleList() {
        return mArticleList;
    }


    /**
     * 重新加载
     */
    @Override
    public void reloadData() {
        loadHomeData();
    }

    /**
     * 获取首页数据
     */
    public void loadHomeData() {
        if (!mRefresh) {
            loadState.setValue(LoadState.LOADING);
        }
        mPageNum = 0;
        loadBanner();
        loadTopArticleList();
    }

    /**
     * 刷新
     */
    public void refreshData() {
        loadHomeData();
    }

    /**
     * 加载更多数据
     */
    public void loadMoreData() {
        mPageNum++;
        loadArticleList(mPageNum);
    }


    /**
     * 获取首页轮播图
     */
    private void loadBanner() {

        if (NetworkUtils.isConnected() && NetworkUtils.getWifiEnabled()) {
            loadBannerByNet();
        } else {
            loadBannerByDb();
        }
    }


    /**
     * 从网络接口获取Banner
     */
    private void loadBannerByNet() {
        HttpRequest.getInstance()
                .getBanner()
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HomeBannerEntity>() {
                    @Override
                    public void success(HomeBannerEntity homeBannerEntity) {
                        mBanner.postValue(homeBannerEntity);
                        if (!mRefresh) {
                            loadState.postValue(LoadState.SUCCESS);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

    /**
     * 从数据库获取Banner
     */
    private void loadBannerByDb() {

    }

    /**
     * 获取置顶文章
     */
    private void loadTopArticleList() {
        HttpRequest.getInstance()
                .getTopArticleList()
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<List<ArticleBean>>>() {
                    @Override
                    public void success(HttpBaseResponse<List<ArticleBean>> mArticleBean) {

                        if (mArticleBean.data != null && mArticleBean.data.size() != 0) {

                            mList.clear();
                            mList.addAll(mArticleBean.data);

                            for (ArticleBean bean :mList){
                                bean.setTop(true);
                            }

                            HttpBaseResponse<ArticleListBean> mArticleListBean = new HttpBaseResponse<>();
                            mArticleListBean.errorCode = mArticleBean.errorCode;
                            mArticleListBean.data.setDatas(mList);
                            mArticleList.postValue(mArticleListBean.data);

                            //获取首页文章
                            loadArticleList(0);
                            if (!mRefresh) {
                                loadState.postValue(LoadState.SUCCESS);
                            }
                        } else {
                            if (!mRefresh) {
                                loadState.postValue(LoadState.NO_DATA);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadArticleList(0);
                    }
                });
    }

    /**
     * 获取首页文章列表
     *
     * @param page
     */
    private void loadArticleList(int page) {
        HttpRequest.getInstance()
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<ArticleListBean>>() {
                    @Override
                    public void success(HttpBaseResponse<ArticleListBean> mArticleListBean) {

                        if (mArticleListBean.data.getDatas() != null && mArticleListBean.data.getDatas().size() != 0) {

                            mList.addAll(mArticleListBean.data.getDatas());
                            mArticleListBean.data.setDatas(mList);
                            mArticleList.postValue(mArticleListBean.data);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadState.postValue(LoadState.ERROR);
                    }
                });
    }
}