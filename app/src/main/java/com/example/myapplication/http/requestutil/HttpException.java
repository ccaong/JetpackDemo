package com.example.myapplication.http.requestutil;

import android.text.TextUtils;

import com.example.myapplication.http.HttpBaseResponse;

/**
 * @author devel
 */
public class HttpException extends RuntimeException {

    public HttpException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return TextUtils.isEmpty(message) ? "" : message;
    }


    private int code;
    private String message;
    private HttpBaseResponse response;

}
