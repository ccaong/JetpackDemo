package com.example.myapplication;

import android.content.Context;

import androidx.multidex.MultiDexApplication;

import com.example.myapplication.http.data.HttpResponseInterface;
import com.example.myapplication.http.request.HttpFactory;
import com.example.myapplication.http.request.ServerAddress;
import com.google.gson.Gson;

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
