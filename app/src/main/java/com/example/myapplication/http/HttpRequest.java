package com.example.myapplication.http;


/**
 * @author devel
 */
public class HttpRequest {

    private static API Instance;

    public static API getInstance() {
        if (Instance == null) {
            synchronized (HttpRequest.class) {
                if (Instance == null) {
                    Instance = HttpFactory.getInstance(API.class);
                }
            }
        }
        return Instance;
    }

    public static API getInstance(String url) {
        return HttpFactory.getChangeUrlInstance(url, API.class);
    }

}
