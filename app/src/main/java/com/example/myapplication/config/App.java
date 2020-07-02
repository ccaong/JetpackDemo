package com.example.myapplication.config;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
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
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshInitializer;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;


/**
 * @author : devel
 * @date : 2020/2/19 11:30
 * @desc :application
 */

public class App extends MultiDexApplication {
    private static Context context;
    public static boolean firstOpen;

    @Override
    public void onCreate() {
        super.onCreate();
        firstOpen = true;
        context = this;
        initActivityManager();
        init();
    }

    public static Context getContext() {
        return context;
    }

    /**
     * 管理Activity
     */
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

    /**
     * 一些第三方库和本地代码的初始化设置
     */
    private void init() {
        Hawk.init(context).build();

        setHttpConfig();

        Phoenix.config()
                .imageLoader((mContext, imageView, imagePath, type) -> Glide.with(mContext)
                        .load(imagePath)
                        .into(imageView));
    }

    /**
     * 请求配置
     */
    public static void setHttpConfig() {

        HttpFactory.HTTP_HOST_URL = ServerAddress.getApiDefaultHost();
        HttpFactory.httpResponseInterface = (gson, response) -> {

            if (firstOpen) {
                firstOpen = false;
                return response;
            }

            HttpBaseResponse httpResponse = gson.fromJson(response, HttpBaseResponse.class);
            if (httpResponse.errorCode != 0) {
                throw new HttpException(httpResponse.errorCode,httpResponse.errorMsg);
            }
            return gson.toJson(httpResponse.data);
        };
    }

    /**
     * 设置上拉加载和下拉刷新的样式
     */
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            //全局设置主题颜色
            layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
            return new ClassicsHeader(context);
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setDrawableSize(20);
        });

        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {
            layout.setEnableFooterFollowWhenLoadFinished(true);
            layout.setEnableAutoLoadMore(false);
        });
    }
}
