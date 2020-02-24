package com.example.myapplication.ui.nav.todo;

import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.enums.LoadState;
import com.example.myapplication.http.bean.ToDoListBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.schedulers.Schedulers;

/**
 * @author devel
 */
public class ToDoViewModel extends BaseViewModel {

    private MutableLiveData<HttpBaseResponse<ToDoListBean>> toDoBean;
    private MutableLiveData<Integer> type;
    private MutableLiveData<Integer> priority;

    private int mPage;
    private List<ToDoListBean.ToDoData> mList;

    public ToDoViewModel() {
        toDoBean = new MutableLiveData<>();
        mList = new ArrayList<>();
        type = new MutableLiveData<>(0);
        priority = new MutableLiveData<>(0);
    }

    public void setType(int i) {
        this.type = new MutableLiveData<>(i);
        loadToDoList();
    }

    public void setPriority(int i) {
        this.priority = new MutableLiveData<>(i);
        loadToDoList();
    }

    /**
     * 获取待办事项列表
     *
     * @return
     */
    public LiveData<HttpBaseResponse<ToDoListBean>> getToDoBean() {
        return toDoBean;
    }

    /**
     * 加载更多
     */
    public void loadMore() {
        mRefresh = true;
        mPage++;
        loadData();
    }

    /**
     * 刷新
     */
    public void refresh() {
        mRefresh = true;
        loadToDoList();
    }


    @Override
    public void reloadData() {
        mRefresh = false;
        loadToDoList();
    }

    public void loadToDoList() {
        mPage = 0;
        loadData();
    }

    private void loadData() {

        if (!mRefresh) {
            loadState.postValue(LoadState.LOADING);
        }
        HttpRequest.getInstance()
                .getToDoList(mPage, type.getValue(), priority.getValue())
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<ToDoListBean>>() {
                    @Override
                    public void success(HttpBaseResponse<ToDoListBean> bean) {
                        // TODO: 2020/2/24 待办
                        if (bean.errorCode == 0) {

                            if (!mRefresh) {
                                loadState.postValue(LoadState.SUCCESS);
                            }
                            if (mPage == 0) {
                                mList.clear();
                                toDoBean.postValue(bean);
                            } else {
                                mList.addAll(bean.data.getDatas());
                                bean.data.setDatas(mList);
                                toDoBean.postValue(bean);
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

    /**
     * 更新
     */
    public void updateToDoData(ToDoListBean.ToDoData bean) {
        HttpRequest.getInstance()
                .updateToDoList(bean.getId(), bean.getTitle(), bean.getContent(),
                        bean.getDateStr(), bean.getStatus(),
                        bean.getType(), bean.getPriority())
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<ToDoListBean>>() {
                    @Override
                    public void success(HttpBaseResponse<ToDoListBean> bean) {
                    }
                });
    }
}
