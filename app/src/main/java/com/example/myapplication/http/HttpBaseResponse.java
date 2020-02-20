package com.example.myapplication.http;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * 数据封装类型
 * @author devel
 */
public class HttpBaseResponse<T> implements Serializable {

    @Expose
    public int code;

    @Expose
    public String msg;

    @Expose
    public T extend;

}