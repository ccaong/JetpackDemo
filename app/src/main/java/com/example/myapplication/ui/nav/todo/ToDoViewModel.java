package com.example.myapplication.ui.nav.todo;


import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.config.LoadState;
import com.example.myapplication.bean.responsebean.ToDoListBean;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.util.CommonUtils;
import com.example.myapplication.util.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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
    private MutableLiveData<ToDoListBean> toDoListBean;

    private MutableLiveData<Object> toDoChangeStatus;


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
        toDoChangeStatus = new MutableLiveData<>();

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
        mPage = 1;
        loadToDoList();
    }

    public void setQueryPriority(int i) {
        this.queryPriority = new MutableLiveData<>(i);
        mPage = 1;
        loadToDoList();
    }


    public LiveData<ToDoListBean.ToDoData> getData() {
        return toDoData;
    }

    public LiveData<Object> getToDoChangeStatus() {
        return toDoChangeStatus;
    }

    /**
     * 获取待办事项列表
     *
     * @return
     */
    public LiveData<ToDoListBean> getToDoListBean() {
        return toDoListBean;
    }


    /**
     * 刷新
     */
    public void refreshData(Boolean refresh) {
        if (refresh) {
            mPage = 1;
        } else {
            mPage++;
        }
        mRefresh = true;
        loadData();
    }

    @Override
    public void reloadData() {
        loadToDoList();
    }

    /**
     * 第一次加载数据
     */
    public void loadToDoList() {
        loadState.postValue(LoadState.LOADING);
        mPage = 1;
        mRefresh = false;
        loadData();
    }

    /**
     * 获取待办清单
     */
    private void loadData() {

        //判断网络
        if (!NetworkUtils.isConnected()) {
            loadState.postValue(LoadState.NO_NETWORK);
            return;
        }

        HttpRequest.getInstance()
                .getToDoList(mPage, queryType.getValue(), queryPriority.getValue())
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<ToDoListBean>() {
                    @Override
                    public void success(ToDoListBean bean) {

                        if (CommonUtils.isListEmpty(bean.getDatas())) {
                            loadState.postValue(LoadState.NO_DATA);
                            return;
                        }
                        //设置加载状态
                        loadState.postValue(LoadState.SUCCESS);
                        if (mPage == 1) {

                            mList.clear();
                            mList.addAll(bean.getDatas());
                            toDoListBean.postValue(bean);
                        } else {

                            mList.addAll(bean.getDatas());
                            bean.setDatas(mList);
                            toDoListBean.postValue(bean);
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
     * 更新数据
     */
    public void updateTodo() {

        toDoData.getValue().setTitle(title.getValue());
        toDoData.getValue().setContent(content.getValue());

        toDoData.getValue().setPriority(priority.getValue());
        toDoData.getValue().setType(type.getValue());
        toDoData.getValue().setStatus(status.getValue());

        updateToDoData(toDoData.getValue());
    }

    /**
     * 提交更新
     */
    public void updateToDoData(ToDoListBean.ToDoData bean) {

        HttpRequest.getInstance()
                .updateToDoList(bean.getId(), bean.getTitle(), bean.getContent(),
                        bean.getDateStr(), bean.getStatus(),
                        bean.getType(), bean.getPriority())
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<Object>() {
                    @Override
                    public void success(Object bean) {
                        toDoChangeStatus.postValue(bean);
                    }
                });
    }

    /**
     * 删除一个ToDo
     */
    public void deleteToDo() {
        HttpRequest.getInstance()
                .deleteToDo(toDoData.getValue().getId())
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<Object>() {
                    @Override
                    public void success(Object bean) {
                        toDoChangeStatus.postValue(bean);
                    }
                });
    }
}
