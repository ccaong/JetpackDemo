package com.example.myapplication.http;

import com.google.gson.Gson;

/**
 * @author devel
 */
public interface HttpResponseInterface {

    /**
     * 1
     * @param gson
     * @param response
     * @return
     */
    String getResponseData(Gson gson, String response);

}
