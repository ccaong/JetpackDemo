package com.example.myapplication.http.data;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 数据封装类型
 *
 * @author devel
 */
public class HttpBaseResponse<T> implements Serializable {

    @Expose
    public int errorCode;

    @Expose
    public String errorMsg;

    @Expose
    public T data;

}