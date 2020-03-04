package com.example.myapplication.ui.nav.todo.todocontent;


import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.bean.responsebean.ToDoListBean;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

/**
 * @author devel
 */
public class ToDoContentViewModel extends BaseViewModel {


    private MutableLiveData<Object> toDoChangeStatus;

    /**
     * 待办事项
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


    public ToDoContentViewModel() {
        toDoChangeStatus = new MutableLiveData<>();
        toDoData = new MutableLiveData<>();
        initData();
    }

    private void initData() {
        status = new MutableLiveData<>(0);
        type = new MutableLiveData<>(0);
        priority = new MutableLiveData<>(0);
        title = new MutableLiveData<>();
        content = new MutableLiveData<>();
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

    public LiveData<Object> getToDoChangeStatus() {
        return toDoChangeStatus;
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
