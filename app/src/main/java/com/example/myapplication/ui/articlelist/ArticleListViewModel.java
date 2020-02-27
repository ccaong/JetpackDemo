package com.example.myapplication.ui.articlelist;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.enums.RefreshState;
import com.example.myapplication.http.bean.ArticleBean;
import com.example.myapplication.http.bean.ArticleListBean;
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
public class ArticleListViewModel extends BaseViewModel {

    private int mType = 0;
    private int mPage = 0;
    private int mId = 0;
    private MutableLiveData<ArticleListBean> mArticleList;
    private List<ArticleBean> mList;

    public ArticleListViewModel() {
        mArticleList = new MediatorLiveData<>();
        mList = new ArrayList<>();
    }

    public void setType(int type) {
        this.mType = type;
    }

    public void setId(int id) {
        this.mId = id;
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
        mPage = 0;
        mRefresh = false;
        loadArticleList();
        loadState.postValue(LoadState.LOADING);
    }


    /**
     * 加载微信文章数据
     */
    private void loadArticleList() {
        //判断网络
        if (!NetworkUtils.isConnected()) {
            loadState.postValue(LoadState.NO_NETWORK);
            return;
        }

        if (mType == 0) {
            loadWeChatArticleList();
        } else {
            loadSystemArticleList();
        }
    }

    /**
     * 加载微信公众号数据
     */
    private void loadWeChatArticleList() {

        HttpRequest.getInstance()
                .getWechatArticleList(mId, mPage)
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<ArticleListBean>>() {
                    @Override
                    public void success(HttpBaseResponse<ArticleListBean> mArticleListBean) {

                        if (mArticleListBean != null && mArticleListBean.errorCode == 0) {
                            loadState.postValue(LoadState.SUCCESS);

                            if (mPage == 0) {
                                //第一次加载或刷新成功
                                //清空列表，重新载入数据，设置刷新成功状态
                                mList.clear();
                                mList.addAll(mArticleListBean.data.getDatas());
                                mArticleList.postValue(mArticleListBean.data);

                                //设置刷新状态
                                refreshState.postValue(RefreshState.REFRESH_END);

                            } else {
                                //下拉加载更多成功
                                //添加数据，设置下拉加载成功状态
                                mList.addAll(mArticleListBean.data.getDatas());
                                mArticleListBean.data.setDatas(mList);
                                mArticleList.postValue(mArticleListBean.data);
                                //设置刷新状态
                                refreshState.postValue(RefreshState.LOAD_MORE_END);
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

    /**
     * 加载体系文章数据
     */
    private void loadSystemArticleList() {

        HttpRequest.getInstance()
                .getWechatArticleList(mId, mPage)
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<ArticleListBean>>() {
                    @Override
                    public void success(HttpBaseResponse<ArticleListBean> mArticleListBean) {

                        if (mArticleListBean != null && mArticleListBean.errorCode == 0) {
                            loadState.postValue(LoadState.SUCCESS);

                            if (mPage == 0) {
                                //第一次加载或刷新成功
                                //清空列表，重新载入数据，设置刷新成功状态
                                mList.clear();
                                mList.addAll(mArticleListBean.data.getDatas());
                                mArticleList.postValue(mArticleListBean.data);

                                //设置刷新状态
                                refreshState.postValue(RefreshState.REFRESH_END);

                            } else {
                                //下拉加载更多成功
                                //添加数据，设置下拉加载成功状态
                                mList.addAll(mArticleListBean.data.getDatas());
                                mArticleListBean.data.setDatas(mList);
                                mArticleList.postValue(mArticleListBean.data);
                                //设置刷新状态
                                refreshState.postValue(RefreshState.LOAD_MORE_END);
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
