package com.example.myapplication.http.request;

import com.example.myapplication.entity.HomeBannerEntity;
import com.example.myapplication.entity.WeChatListEntity;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * 网络请求接口地址
 *
 * @author devel
 */
public interface RequestAddress {


    @Multipart
    @POST("hd/uploadFile")
    Observable<Object> upload(@Part MultipartBody.Part file, @Part("type") RequestBody type);

    @GET("HPImageArchive.aspx")
    Observable<Object> getImage(@Query("format") String format, @Query("idx") int idx, @Query("n") int n);


    /**
     * 获取首页banner
     * @return
     */
    @GET("banner/json")
    Observable<HomeBannerEntity> getBanner();

    @GET("wxarticle/chapters/json")
    Observable<WeChatListEntity> getWechatList();
}
