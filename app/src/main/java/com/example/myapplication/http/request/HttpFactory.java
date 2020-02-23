package com.example.myapplication.http.request;


import com.example.myapplication.http.data.HttpResponseInterface;
import com.example.myapplication.http.httptool.AddCookiesInterceptor;
import com.example.myapplication.http.httptool.HttpInterceptor;
import com.example.myapplication.http.httptool.ResponseConverterFactory;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;


/**
 * @author devel
 * 网络请求
 */
public class HttpFactory {

    public static String HTTP_HOST_URL = "";
    public static HttpResponseInterface httpResponseInterface = null;

    private HttpFactory() {
    }

    /**
     * 设置HttpClient
     */
    private static OkHttpClient HTTP_CLIENT =
            new Builder()
                    //添加自定义拦截器
                    .addInterceptor(new HttpInterceptor())
                    .addInterceptor(new AddCookiesInterceptor())
                    //设置超时时间
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build();

    private static Retrofit retrofit = null;

    public static <T> T getChangeUrlInstance(String url, Class<T> service) {
        return new Retrofit.Builder().baseUrl(url)
                .addConverterFactory(ResponseConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(HTTP_CLIENT)
                .build()
                .create(service);
    }

    public static <T> T getInstance(Class<T> service) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder().baseUrl(HTTP_HOST_URL)
                    .addConverterFactory(ResponseConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(HTTP_CLIENT)
                    .build();
        }
        return retrofit.create(service);
    }


    @SuppressWarnings("unchecked")
    public static <T> ObservableTransformer<T, T> schedulers() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }
}
