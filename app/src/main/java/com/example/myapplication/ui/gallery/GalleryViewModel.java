package com.example.myapplication.ui.gallery;

import android.app.Application;

import com.example.myapplication.ThreadManager;
import com.example.myapplication.entity.WeChatListEntity;
import com.example.myapplication.http.data.HttpDisposable;
import com.example.myapplication.http.request.HttpRequest;
import com.example.myapplication.room.AppDataBase;
import com.example.myapplication.room.dao.WeChatDao;
import com.example.myapplication.util.NetworkUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.schedulers.Schedulers;

/**
 * @author devel
 */

public class GalleryViewModel extends AndroidViewModel {


    public GalleryViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 获取微信公众号列表
     */
    public void loadWeChatList() {


    }

}