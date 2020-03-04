package com.example.myapplication.ui.home;

import android.util.Log;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.config.LoadState;
import com.example.myapplication.bean.responsebean.ArticleBean;
import com.example.myapplication.bean.responsebean.ArticleListBean;
import com.example.myapplication.bean.responsebean.HomeBanner;
import com.example.myapplication.bean.responsebean.home.BannerData;
import com.example.myapplication.bean.responsebean.home.HomeData;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author devel
 */
public class HomeViewModel extends BaseViewModel {

    private MutableLiveData<List<HomeData>> mHomeList;
    private List<HomeData> mList;

    /**
     * 请求页码
     */
    private int mPageNum;

    public HomeViewModel() {
        Log.e("生命周期", "ViewModel初始化");
        mHomeList = new MutableLiveData<>();
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
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<List<HomeBanner>>() {
                    @Override
                    public void success(List<HomeBanner> homeBannerEntity) {
                        mList.clear();

                        HomeData homeData = new HomeData();
                        homeData.setBannerData(new BannerData(homeBannerEntity));
                        mList.add(homeData);

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
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<List<ArticleBean>>() {
                    @Override
                    public void success(List<ArticleBean> mArticleBean) {

                        if (mArticleBean != null && mArticleBean.size() != 0) {

//                            for (ArticleBean bean : mArticleBean) {
//                                bean.setTop(true);
//                                HomeData homeData = new HomeData();
//                                homeData.setArticleList(bean);
//                                mList.add(homeData);
//                            }
                            HomeData homeData = new HomeData();
                            HomeData.TopArticle topArticle = new HomeData.TopArticle();
                            topArticle.setName("置顶文章");
                            topArticle.setArticleBeanList(mArticleBean);
                            homeData.setTopArticleList(topArticle);

                            mList.add(homeData);

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
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<ArticleListBean>() {
                    @Override
                    public void success(ArticleListBean mArticleListBean) {

                        if (mArticleListBean.getDatas() != null && mArticleListBean.getDatas().size() != 0) {
                            for (ArticleBean bean : mArticleListBean.getDatas()) {
                                HomeData homeData = new HomeData();
                                homeData.setArticleList(bean);
                                mList.add(homeData);
                            }

                            List<HomeData> homeData = new ArrayList<>();
                            homeData.addAll(mList);
                            mHomeList.postValue(homeData);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadState.postValue(LoadState.ERROR);
                    }
                });
    }
}