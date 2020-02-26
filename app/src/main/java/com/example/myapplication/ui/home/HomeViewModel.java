package com.example.myapplication.ui.home;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.http.bean.ArticleBean;
import com.example.myapplication.http.bean.ArticleListBean;
import com.example.myapplication.http.bean.HomeBanner;
import com.example.myapplication.http.bean.home.BannerData;
import com.example.myapplication.http.bean.home.HomeData;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.NetworkUtils;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.schedulers.Schedulers;

/**
 * @author devel
 */
public class HomeViewModel extends BaseViewModel {

    private List<HomeBanner> mBannerList;
    private List<ArticleBean> mArticleList;

    private MutableLiveData<List<HomeData>> mHomeList;
    private List<HomeData> mList;

    /**
     * 请求页码
     */
    private int mPageNum;

    public HomeViewModel() {

        mHomeList = new MutableLiveData<>();

        mBannerList = new ArrayList<>();
        mArticleList = new ArrayList<>();

        mList = new ArrayList<>();
    }


    public LiveData<List<HomeData>> getHomeList() {
        return mHomeList;
    }


    /**
     * 重新加载
     */
    @Override
    public void reloadData() {
        mRefresh = false;
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
    }

    /**
     * 刷新
     */
    public void refreshData(boolean refresh) {
        mRefresh = true;
        if (refresh) {
            mPageNum = 0;
            loadHomeData();
        } else {
            mPageNum++;
            loadArticleList(mPageNum);
        }

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
                .subscribe(new HttpDisposable<HttpBaseResponse<List<HomeBanner>>>() {
                    @Override
                    public void success(HttpBaseResponse<List<HomeBanner>> homeBannerEntity) {

                        mList.clear();
                        HomeData homeData = new HomeData();
                        homeData.setBannerData(new BannerData(homeBannerEntity.data));
                        mList.add(homeData);
                        mHomeList.postValue(mList);

                        if (!mRefresh) {
                            loadState.postValue(LoadState.SUCCESS);
                        }
                        //获取置顶文章
                        loadTopArticleList();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //获取置顶文章
                        loadTopArticleList();
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

                            for (ArticleBean bean : mArticleBean.data) {
                                bean.setTop(true);
                                HomeData homeData = new HomeData();
                                homeData.setArticleList(bean);
                                mList.add(homeData);
                            }
                            mHomeList.postValue(mList);

                            //获取首页文章
                            loadArticleList(0);
                            if (!mRefresh) {
                                loadState.postValue(LoadState.SUCCESS);
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
                            for (ArticleBean bean : mArticleListBean.data.getDatas()) {
                                HomeData homeData = new HomeData();
                                homeData.setArticleList(bean);
                                mList.add(homeData);
                            }
                            mHomeList.postValue(mList);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadState.postValue(LoadState.ERROR);
                    }
                });
    }
}