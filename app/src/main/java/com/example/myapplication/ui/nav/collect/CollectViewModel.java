package com.example.myapplication.ui.nav.collect;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.config.LoadState;
import com.example.myapplication.bean.responsebean.CollectArticleBean;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;
import com.example.myapplication.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Response;

/**
 * @author devel
 */

public class CollectViewModel extends BaseViewModel {


    private MutableLiveData<CollectArticleBean> mArticleList;
    private List<CollectArticleBean.CollectBean> mList;

    /**
     * 请求页码
     */
    private int mPage = 0;

    public CollectViewModel() {
        mArticleList = new MutableLiveData<>();
        mList = new ArrayList<>();
    }

    public LiveData<CollectArticleBean> getArticleList() {
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
     * 获取收藏文章列表
     */
    private void loadArticleList() {
        if (!NetworkUtils.isConnected()) {
            loadState.postValue(LoadState.NO_NETWORK);
            return;
        }

        HttpRequest.getInstance()
                .getCollectList(mPage)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<CollectArticleBean>() {
                    @Override
                    public void success(CollectArticleBean mArticleListBean) {

                        if (mArticleListBean != null) {
                            loadState.postValue(LoadState.SUCCESS);
                            if (CommonUtils.isListEmpty(mArticleListBean.getDatas())) {
                                loadState.postValue(LoadState.NO_DATA);
                                return;
                            }

                            if (mPage == 0) {
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
                            loadState.postValue(LoadState.ERROR);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        loadState.postValue(LoadState.ERROR);
                        errorMsg.postValue(e.getMessage());
                    }
                });
    }

    /**
     * 取消收藏
     *
     * @param bean
     */
    public void collectCancel(CollectArticleBean.CollectBean bean) {
        int originId = bean.getOriginId() != 0 ? bean.getOriginId() : -1;
        HttpRequest.getInstance()
                .unCollect(bean.getId(), originId)
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<Response<Void>>() {
                    @Override
                    public void success(Response<Void> mArticleListBean) {
                        CollectArticleBean collectArticleBean = mArticleList.getValue();
                        collectArticleBean.getDatas().remove(bean);
                        if (collectArticleBean.getDatas().size() == 0) {
                            loadState.postValue(LoadState.NO_DATA);
                            return;
                        }
                        mArticleList.postValue(collectArticleBean);
                    }
                });
    }
}