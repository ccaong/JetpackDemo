package com.example.myapplication.http.request;


/**
 * 服务器地址
 *
 * @author devel
 */
public class APIConstants {

    public static String API_DEFAULT_HOST = "https://cn.bing.com/";


    public static String getApiDefaultHost() {
        return API_DEFAULT_HOST;
    }

    public static void setApiDefaultHost(String IP) {
        API_DEFAULT_HOST = "http://" + IP + "/mobileInterface/";
    }

}
