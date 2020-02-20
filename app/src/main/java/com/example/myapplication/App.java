package com.example.myapplication;


import android.content.Context;

import com.example.myapplication.common.APIConstants;
import com.example.myapplication.http.HttpBaseResponse;
import com.example.myapplication.http.requestutil.HttpException;
import com.example.myapplication.http.HttpFactory;
import com.example.myapplication.http.HttpResponseInterface;
import com.google.gson.Gson;

import androidx.multidex.MultiDexApplication;

/**
 * @author : devel
 * @date : 2020/2/19 11:30
 * @desc :
 */
public class App extends MultiDexApplication {

    private static Context context;
    public static String Mac = "";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        setHttpConfig();
    }

    public static Context getContext() {
        return context;
    }


    /**
     * 请求配置
     */
    public static void setHttpConfig() {
        HttpFactory.HTTP_HOST_URL = APIConstants.getApiDefaultHost();
        HttpFactory.httpResponseInterface = new HttpResponseInterface() {
            @Override
            public String getResponseData(Gson gson, String response) {

                return response;

//                HttpBaseResponse httpResponse = gson.fromJson(response, HttpBaseResponse.class);
//                if (httpResponse.code != 0 && httpResponse.code != 104) {
//                    throw new HttpException(httpResponse.msg);
//                }
//
//                return gson.toJson(httpResponse.extend);

            }
        };
    }
}
