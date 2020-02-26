package com.example.myapplication.ui.nav.collect;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.http.bean.ArticleBean;
import com.example.myapplication.http.bean.ArticleListBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.schedulers.Schedulers;

/**
 * @author devel
 */

public class CollectViewModel extends BaseViewModel {


    private MutableLiveData<ArticleListBean> mArticleList;
    private List<ArticleBean> mList;
    /**
     * 请求页码
     */
    private int mPageNum;

    public CollectViewModel() {
        mArticleList = new MutableLiveData<>();
        mList = new ArrayList<>();
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
        loadCollectData();
    }


    public void refreshData() {
        mRefresh = true;
        loadCollectData();
    }

    public void loadMoreData() {
        mRefresh = true;

        mPageNum++;
        loadArticleList(mPageNum);
    }

    /**
     * 获取收藏的文章
     */
    public void loadCollectData() {
        mPageNum = 0;
        loadArticleList(mPageNum);
    }

    /**
     * 获取收藏文章列表
     *
     * @param page
     */
    private void loadArticleList(int page) {
        if (!mRefresh) {
            loadState.postValue(LoadState.LOADING);
        }

        HttpRequest.getInstance()
                .getCollectList(page)
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<ArticleListBean>>() {
                    @Override
                    public void success(HttpBaseResponse<ArticleListBean> mArticleListBean) {

                        if (mArticleListBean.data.getDatas() != null
                                && !CommonUtils.isListEmpty(mArticleListBean.data.getDatas())) {

                            if (!mRefresh) {
                                loadState.postValue(LoadState.SUCCESS);
                            }
                            if (mPageNum == 0) {
                                mList.clear();
                            }
                            mList.addAll(mArticleListBean.data.getDatas());
                            mArticleListBean.data.setDatas(mList);
                            mArticleList.postValue(mArticleListBean.data);
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