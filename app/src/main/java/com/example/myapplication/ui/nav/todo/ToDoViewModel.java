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

    /**
     * 分类（查询条件）
     */
    private MutableLiveData<Integer> queryType;
    /**
     * 级别（查询条件）
     */
    private MutableLiveData<Integer> queryPriority;

    private int mPage;

    private List<ToDoListBean.ToDoData> mList;


    /**
     * 待办清单列表
     */
    private MutableLiveData<HttpBaseResponse<ToDoListBean>> toDoListBean;


    /**
     * 被选中的某一条待办事项
     */
    public MutableLiveData<ToDoListBean.ToDoData> toDoData;


    /**
     * 是否完成
     */
    public MutableLiveData<Integer> status;

    /**
     * 分类
     */
    public MutableLiveData<Integer> type;

    /**
     * 级别
     */
    public MutableLiveData<Integer> priority;

    /**
     * 标题内容
     */
    public MutableLiveData<String> title;
    public MutableLiveData<String> content;


    public ToDoViewModel() {
        toDoListBean = new MutableLiveData<>();
        toDoData = new MutableLiveData<>();
        mList = new ArrayList<>();
        queryType = new MutableLiveData<>(0);
        queryPriority = new MutableLiveData<>(0);

        initData();
    }

    private void initData() {
        status = new MutableLiveData<>(0);
        type = new MutableLiveData<>(0);
        priority = new MutableLiveData<>(0);
        title = new MutableLiveData<>();
        content = new MutableLiveData<>();
    }

    public void setQueryType(int i) {
        this.queryType = new MutableLiveData<>(i);
        loadToDoList();
    }

    public void setQueryPriority(int i) {
        this.queryPriority = new MutableLiveData<>(i);
        loadToDoList();
    }


    /**
     * 设置选中的数据
     *
     * @param data
     */
    public void setToDoData(ToDoListBean.ToDoData data) {
        this.toDoData.postValue(data);

        status.postValue(data.getStatus());
        type.postValue(data.getType());
        priority.postValue(data.getPriority());
        title.postValue(data.getTitle());
        content.postValue(data.getContent());
    }


    public LiveData<ToDoListBean.ToDoData> getData() {
        return toDoData;
    }

    /**
     * 获取待办事项列表
     *
     * @return
     */
    public LiveData<HttpBaseResponse<ToDoListBean>> getToDoListBean() {
        return toDoListBean;
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
                .getToDoList(mPage, queryType.getValue(), queryPriority.getValue())
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<ToDoListBean>>() {
                    @Override
                    public void success(HttpBaseResponse<ToDoListBean> bean) {
                        if (bean.errorCode == 0) {
                            if (!mRefresh) {
                                loadState.postValue(LoadState.SUCCESS);
                            }
                            if (mPage == 0) {
                                mList.clear();
                                toDoListBean.postValue(bean);
                            } else {
                                mList.addAll(bean.data.getDatas());
                                bean.data.setDatas(mList);
                                toDoListBean.postValue(bean);
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


    public void updateTodo() {

        toDoData.getValue().setTitle(title.getValue());
        toDoData.getValue().setContent(content.getValue());

        toDoData.getValue().setPriority(priority.getValue());
        toDoData.getValue().setType(type.getValue());
        toDoData.getValue().setStatus(status.getValue());

        updateToDoData(toDoData.getValue());
    }

    /**
     * 改变优先级
     */
    public void changeToDoPriority() {
        if (priority.getValue() == 0) {
            priority.postValue(1);
        } else {
            priority.postValue(0);
        }
    }

    /**
     * 删除一个ToDo
     */
    public void deleteToDo() {
        HttpRequest.getInstance()
                .deleteToDo(toDoData.getValue().getId())
                .subscribeOn(Schedulers.io())
                .subscribe(new HttpDisposable<HttpBaseResponse<Object>>() {
                    @Override
                    public void success(HttpBaseResponse<Object> bean) {

                    }
                });
    }


}
