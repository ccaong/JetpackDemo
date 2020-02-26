package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.http.data.HttpResponseInterface;
import com.example.myapplication.http.httptool.HttpException;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.ServerAddress;
import com.example.myapplication.manager.MyActivityManager;
import com.google.gson.Gson;
import com.guoxiaoxing.phoenix.core.listener.ImageLoader;
import com.guoxiaoxing.phoenix.picker.Phoenix;
import com.orhanobut.hawk.Hawk;

import androidx.multidex.MultiDexApplication;


/**
 * @author : devel
 * @date : 2020/2/19 11:30
 * @desc :
 */

public class App extends MultiDexApplication {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initActivityManager();
        init();
    }

    public static Context getContext() {
        return context;
    }

    private void initActivityManager() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                MyActivityManager.getInstance().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void init() {
        Hawk.init(context).build();

        setHttpConfig();

        Phoenix.config()
                .imageLoader(new ImageLoader() {
                    @Override
                    public void loadImage(Context mContext, ImageView imageView
                            , String imagePath, int type) {
                        Glide.with(mContext)
                                .load(imagePath)
                                .into(imageView);
                    }
                });
    }

    /**
     * 请求配置
     */
    public static void setHttpConfig() {

        HttpFactory.HTTP_HOST_URL = ServerAddress.getApiDefaultHost();
        HttpFactory.httpResponseInterface = new HttpResponseInterface() {
            @Override
            public String getResponseData(Gson gson, String response) {
                return response;
//                HttpBaseResponse httpResponse = gson.fromJson(response, HttpBaseResponse.class);
//                if (httpResponse.errorCode != 0 && httpResponse.errorCode != 104) {
//                    throw new HttpException(httpResponse.errorMsg);
//                }
//                return gson.toJson(httpResponse.data);
            }
        };
    }

}
