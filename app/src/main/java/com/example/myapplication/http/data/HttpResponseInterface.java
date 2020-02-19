package com.example.myapplication.http.data;

import com.google.gson.Gson;

/**
 * @author devel
 */
public interface HttpResponseInterface {

    /**
     * 获取处理掉code和msg后的信息
     *
     * @param gson
     * @param response
     * @return
     */
    String getResponseData(Gson gson, String response);

}
