package com.example.myapplication.ui.notifications;

import com.example.myapplication.ThreadManager;
import com.example.myapplication.entity.City;
import com.example.myapplication.util.LocalJsonAnalyzeUtil;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import static com.example.myapplication.App.getContext;

/**
 * @author devel
 */
public class NotificationsViewModel extends ViewModel {

    private ThreadManager.ThreadPool threadPool = ThreadManager.getThreadPool();

    public NotificationsViewModel() {


    }


    public void init() {

//        initData();
    }

    private void initData() {
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                City data = LocalJsonAnalyzeUtil.JsonToObject(getContext(),
                        "address.json", City.class);


            }
        });

    }

}