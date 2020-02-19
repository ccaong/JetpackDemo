package com.example.myapplication.http.httptool;

import android.text.TextUtils;

import com.example.myapplication.http.request.HttpFactory;
import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 处理服务器返回数据
 * 将数据转换成对象
 *
 * @author devel
 */
public class ResponseConverterFactory extends Converter.Factory {

    private final Gson mGson;

    public ResponseConverterFactory(Gson gson) {
        this.mGson = gson;
    }

    public static ResponseConverterFactory create() {
        return create(new Gson());
    }

    public static ResponseConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new ResponseConverterFactory(gson);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new BaseResponseBodyConverter(type);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        return GsonConverterFactory.create().requestBodyConverter(type, parameterAnnotations, methodAnnotations, retrofit);
    }

    private class BaseResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private Type mType;

        private BaseResponseBodyConverter(Type mType) {
            this.mType = mType;
        }

        @Override
        public T convert(ResponseBody response) {
            Object object;
            try {
                String strResponse = response.string();
                if (TextUtils.isEmpty(strResponse)) {
                    throw new HttpException("返回值是空的—-—");
                }

                if (HttpFactory.httpResponseInterface == null) {
                    throw new HttpException("请实现接口HttpResponseInterface—-—");
                } else {
                    String strData = HttpFactory.httpResponseInterface.getResponseData(mGson, strResponse);
                    object = mGson.fromJson(strData, mType);
                }
            } catch (Exception e) {
                throw new HttpException(e.getMessage());
            } finally {
                response.close();
            }
            return (T) object;
        }
    }
}
