package com.example.myapplication.http.request;


/**
 * @author devel
 */
public class HttpRequest {

    private static ApiAddress Instance;

    public static ApiAddress getInstance() {
        if (Instance == null) {
            synchronized (HttpRequest.class) {
                if (Instance == null) {
                    Instance = HttpFactory.getInstance(ApiAddress.class);
                }
            }
        }
        return Instance;
    }

    public static ApiAddress getInstance(String url) {
        return HttpFactory.getChangeUrlInstance(url, ApiAddress.class);
    }

}
