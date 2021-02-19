package com.example.myapplication.http.httptool;

import android.util.Log;

import com.example.myapplication.config.Constants;
import com.example.myapplication.bean.responsebean.LoginBean;

import com.example.myapplication.util.NetworkUtils;
import com.orhanobut.hawk.Hawk;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

/**
 * 自定义请求拦截器
 *
 * @author devel
 */
public class HttpInterceptor implements Interceptor {

    private static final Charset UTF8 = Charset.forName("UTF-8");
    private static String REQUEST_TAG = "请求";

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!NetworkUtils.isConnected()) {
            throw new HttpException("网络连接异常，请检查网络后重试");
        }

        Request request = chain.request();
        request = getHeaderRequest(request);
        logRequest(request);
        Response response = chain.proceed(request);
        if (!response.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();
            for (String header : response.headers("Set-Cookie")) {
                cookies.add(header);
            }
            Hawk.put(Constants.HawkCode.COOKIE, cookies);
        }
        logResponse(response);
        return response;
    }

    /**
     * 添加header
     */
    public Request getHeaderRequest(Request request) {
        LoginBean loginData = Hawk.get(Constants.HawkCode.LOGIN_DATA);
        Request headRequest;
        if (loginData != null) {
            headRequest = request
                    .newBuilder()
                    .addHeader("token", loginData.getToken())
                    .addHeader("Cookie", "loginUserName=" + loginData.getUsername())
                    .addHeader("Cookie", "loginUserPassword=" + loginData.getPassword())
                    .build();
        } else {
            headRequest = request
                    .newBuilder()
                    .addHeader("platform", "android")
                    .addHeader("version", "1.0")
                    .build();
        }
        return headRequest;
    }

    /**
     * 打印请求信息
     *
     * @param request
     */
    private void logRequest(Request request) {
        Log.d(REQUEST_TAG + "method", request.method());
        Log.d(REQUEST_TAG + "url", request.url().toString());
        Log.d(REQUEST_TAG + "header", request.headers().toString());
        if (request.method().equals("GET")) {
            return;
        }
        try {
            RequestBody requestBody = request.body();
            String parameter = null;
            Buffer buffer = new Buffer();
            requestBody.writeTo(buffer);
            parameter = buffer.readString(UTF8);
            buffer.flush();
            buffer.close();
            Log.d(REQUEST_TAG + "参数", parameter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 打印返回结果
     *
     * @param response
     */
    private void logResponse(Response response) {
        try {
            ResponseBody responseBody = response.body();
            String rBody = null;
            BufferedSource source = responseBody.source();
            source.request(Long.MAX_VALUE);
            Buffer buffer = source.buffer();
            Charset charset = UTF8;
            MediaType contentType = responseBody.contentType();
            if (contentType != null) {
                try {
                    charset = contentType.charset(UTF8);
                } catch (UnsupportedCharsetException e) {
                    e.printStackTrace();
                }
            }
            rBody = buffer.clone().readString(charset);
            Log.d(REQUEST_TAG + "返回值", rBody);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

