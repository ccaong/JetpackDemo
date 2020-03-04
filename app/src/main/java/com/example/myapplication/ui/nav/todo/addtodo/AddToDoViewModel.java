package com.example.myapplication.ui.nav.todo.addtodo;

import android.app.DatePickerDialog;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.myapplication.config.App;
import com.example.myapplication.base.viewmodel.BaseViewModel;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.manager.MyActivityManager;
import com.example.myapplication.util.TimeUtils;

import java.util.Calendar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


/**
 * @author devel
 */
public class AddToDoViewModel extends BaseViewModel {

    private MutableLiveData<Object> addData;

    /**
     * 标题内容
     */
    public MutableLiveData<String> title;
    public MutableLiveData<String> content;

    /**
     * 计划完成时间
     */
    public MutableLiveData<String> date;


    /**
     * 分类
     */
    public MutableLiveData<Integer> type;

    /**
     * 级别
     */
    public MutableLiveData<Integer> priority;


    public AddToDoViewModel() {
        addData = new MutableLiveData<>();

        title = new MutableLiveData<>();
        content = new MutableLiveData<>();
        date = new MutableLiveData<>(TimeUtils.getNowDateString());
        type = new MutableLiveData<>(4);
        priority = new MutableLiveData<>(0);
        initDatePicker();
    }

    public LiveData<Object> getAdddata() {
        return addData;
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

    private DatePickerDialog dateDialog;
    private int year, monthOfYear, dayOfMonth;

    private void initDatePicker() {
        // 通过Calendar对象来获取年、月、日、时、分的信息
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 7);
        year = calendar.get(Calendar.YEAR);
        monthOfYear = calendar.get(Calendar.MONTH);
        dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        dateDialog = new DatePickerDialog(MyActivityManager.getInstance().getCurrentActivity(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker arg0, int year, int monthOfYear, int dayOfMonth) {
                String text = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                date.postValue(text);

            }
        }, year, monthOfYear, dayOfMonth);
    }


    /**
     * 改变优先级
     */
    public void changeDoneDate() {
        dateDialog.show();
    }

    /**
     * 添加一条数据
     */
    public void addData() {
        HttpRequest.getInstance()
                .addToDoData(title.getValue(), content.getValue(), date.getValue(), type.getValue(), priority.getValue())
                .compose(HttpFactory.schedulers())
                .subscribe(new HttpDisposable<Object>() {
                    @Override
                    public void success(Object bean) {
                        addData.postValue(bean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(App.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
