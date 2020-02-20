package com.example.myapplication.ui.home;

import com.example.myapplication.base.BaseViewModel;
import com.example.myapplication.entity.ArticleBean;
import com.example.myapplication.entity.ArticleListBean;
import com.example.myapplication.entity.HomeBannerEntity;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.enums.RefreshState;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.schedulers.Schedulers;

/**
 * @author devel
 */
public class HomeViewModel extends BaseViewModel {

    private MutableLiveData<HomeBannerEntity> mBanner;
    private MutableLiveData<ArticleListBean> mArticleList;

    private List<ArticleBean> mList;

    private boolean mRefresh;

    /**
     * 请求页码
     */
    private int mPageNum;

    public HomeViewModel() {
        mBanner = new MutableLiveData<>();
        mArticleList = new MutableLiveData<>();
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
        mRefresh = false;
        loadHomeData();
    }

    /**
     * 刷新
     */
    public void refreshData() {
        mRefresh = true;
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
     * 获取首页数据
     */
    public void loadHomeData() {
        loadBanner();
        loadArticleList(0);

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
     * 获取首页文章列表
     *
     * @param page
     */
    public void loadArticleList(int page) {
        HttpRequest.getInstance()
                .getArticleList(page)
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<ArticleListBean>>() {
                    @Override
                    public void success(HttpBaseResponse<ArticleListBean> mArticleListBean) {

                        if (mArticleListBean.data.getDatas() != null && mArticleListBean.data.getDatas().size() != 0) {

                            if (page == 0) {
                                mList.clear();
                                mList.addAll(mArticleListBean.data.getDatas());
                                mArticleList.postValue(mArticleListBean.data);
                            } else {
                                mList.addAll(mArticleListBean.data.getDatas());
                                mArticleListBean.data.setDatas(mList);
                                mArticleList.postValue(mArticleListBean.data);
                            }

                            if (!mRefresh) {
                                loadState.set(LoadState.SUCCESS);
                            }
                        } else {
                            if (!mRefresh) {
                                loadState.set(LoadState.NO_DATA);
                            }
                        }

                    }
                });
    }
}