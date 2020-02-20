package com.example.myapplication.http.requestutil;

import android.util.Log;

import com.example.myapplication.App;
import com.example.myapplication.requestbean.LoginData;
import com.example.myapplication.utils.NetworkUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;

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
//        request = getHeaderRequest(request);
        logRequest(request);
        Response response = chain.proceed(request);
        logResponse(response);
        return response;
    }

    /**
     * 添加header
     */
    private Request getHeaderRequest(Request request) {
        LoginData loginData = new LoginData();
        Request headRequest;
        if (loginData != null) {
            headRequest = request
                    .newBuilder()
                    .addHeader("platform", "android")
                    .addHeader("version", "1.0")
                    .addHeader("token", loginData.getToken())
                    .addHeader("userId", loginData.getUserId())
                    .addHeader("MAC", App.Mac)
                    .build();
        } else {
            headRequest = request
                    .newBuilder()
                    .addHeader("platform", "android")
                    .addHeader("version", "1.0")
                    .addHeader("MAC", App.Mac)
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

