package com.example.myapplication.http.request;

import com.example.myapplication.entity.ArticleBean;
import com.example.myapplication.entity.ArticleListBean;
import com.example.myapplication.entity.HomeBannerEntity;
import com.example.myapplication.entity.WeChatBean;
import com.example.myapplication.http.data.HttpBaseResponse;
import com.example.myapplication.entity.ImageBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 网络请求接口地址
 *
 * @author devel
 */
public interface ApiAddress {


    @Multipart
    @POST("hd/uploadFile")
    Observable<Object> upload(@Part MultipartBody.Part file, @Part("type") RequestBody type);


    @GET("HPImageArchive.aspx")
    Observable<ImageBean> getImage(@Query("format") String format, @Query("idx") int idx, @Query("n") int n);

    /**
     * 获取首页banner
     *
     * @return
     */
    @GET("banner/json")
    Observable<HomeBannerEntity> getBanner();


    /**
     * 获取置顶文章
     *
     * @return
     */
    @GET("article/top/json")
    Observable<HttpBaseResponse<List<ArticleBean>>> getTopArticleList();

    /**
     * 首页文章列表
     * 方法：GET
     *
     * @param page 页码
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<HttpBaseResponse<ArticleListBean>> getArticleList(@Path("page") int page);


    /**
     * 获取微信公众号列表
     *
     * @return
     */
    @GET("wxarticle/chapters/json")
    Observable<HttpBaseResponse<List<WeChatBean>>> getWechatList();

    /**
     * 获取微信公众号文章列表
     *
     * @return
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<HttpBaseResponse<ArticleListBean>> getWechatArticleList(@Path("id") int id, @Path("page") int page);
}
