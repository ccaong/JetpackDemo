package com.example.myapplication.requestbean;

/**
 * @author : devel
 * @date : 2020/2/19 10:10
 * @desc :
 */
public class Data<T> {

    private T mData;

    private String mErrorMsg;

    public Data(T data, String errorMsg) {
        mData = data;
        mErrorMsg = errorMsg;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public String getErrorMsg() {
        return mErrorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        mErrorMsg = errorMsg;
    }

}
