package com.example.myapplication.http.request;


/**
 * @author devel
 */
public class HttpRequest {

    private static RequestAddress Instance;

    public static RequestAddress getInstance() {
        if (Instance == null) {
            synchronized (HttpRequest.class) {
                if (Instance == null) {
                    Instance = HttpFactory.getInstance(RequestAddress.class);
                }
            }
        }
        return Instance;
    }

    public static RequestAddress getInstance(String url) {
        return HttpFactory.getChangeUrlInstance(url, RequestAddress.class);
    }

}
