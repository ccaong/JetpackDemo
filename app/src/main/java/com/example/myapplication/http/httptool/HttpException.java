package com.example.myapplication.http.httptool;

import android.text.TextUtils;

import com.example.myapplication.http.data.HttpBaseResponse;


/**
 * 自定义异常抛出
 *
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
