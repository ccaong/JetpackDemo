package com.example.myapplication.http.request;

import com.example.myapplication.http.bean.ArticleBean;
import com.example.myapplication.http.bean.ArticleListBean;
import com.example.myapplication.http.bean.CoinBean;
import com.example.myapplication.http.bean.CoinRankBean;
import com.example.myapplication.http.bean.HomeBanner;
import com.example.myapplication.http.bean.ImageBean;
import com.example.myapplication.http.bean.Integral;
import com.example.myapplication.http.bean.LoginBean;
import com.example.myapplication.http.bean.NavigationBean;
import com.example.myapplication.http.bean.ToDoListBean;
import com.example.myapplication.http.bean.WeChatBean;
import com.example.myapplication.http.data.HttpBaseResponse;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 网络请求接口地址
 *
 * @author devel
 */
public interface ApiAddress {


    /**
     * 获取闪屏页图片
     *
     * @param format
     * @param idx
     * @param n
     * @return
     */
    @GET("HPImageArchive.aspx")
    Observable<ImageBean> getImage(@Query("format") String format, @Query("idx") int idx, @Query("n") int n);


    /**
     * 登陆
     *
     * @return
     */
    @FormUrlEncoded
    @POST("user/login")
    Observable<HttpBaseResponse<LoginBean>> Login(@Field("username") String username, @Field("password") String password);

    /**
     * 退出
     *
     * @return
     */
    @GET("user/logout/json")
    Observable<HttpBaseResponse<Object>> logout();


    /**
     * 注册
     *
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<HttpBaseResponse<Object>> register(@Field("username") String username,
                                                  @Field("password") String password,
                                                  @Field("repassword") String repwd);


    /**
     * 获取首页banner
     *
     * @return
     */
    @GET("banner/json")
    Observable<HttpBaseResponse<List<HomeBanner>>> getBanner();


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
     * 获取体系列表
     *
     * @return
     */
    @GET("tree/json")
    Observable<HttpBaseResponse<List<WeChatBean>>> getSystemList();


    /**
     * 获取体系文章
     *
     * @param cid  id
     * @param page 页码
     * @return
     */
    @GET("article/list/{page}/json?cid=60")
    Observable<HttpBaseResponse<ArticleListBean>> getSystemArticle(@Path("page") int page, @Query("cid") String cid);


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
     * @param id   id
     * @param page 页码
     * @return
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<HttpBaseResponse<ArticleListBean>> getWechatArticleList(@Path("id") int id, @Path("page") int page);


    /**
     * 获取导航列表
     *
     * @return
     */
    @GET("navi/json")
    Observable<HttpBaseResponse<List<NavigationBean>>> getNavigationBean();


    /**
     * 获取项目分类列表数据
     *
     * @return 项目分类列表数据
     */
    @GET("project/tree/json")
    Observable<HttpBaseResponse<List<WeChatBean>>> getProjectListData();


    /**
     * 获取收藏的文章
     *
     * @param page 页码
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    Observable<HttpBaseResponse<ArticleListBean>> getCollectList(@Path("page") int page);


    /**
     * 添加一条待办事项
     * 方法：POST
     *
     * @param title    标题
     * @param content  内容
     * @param date     时间
     * @param type     类别
     * @param priority 优先级
     * @return
     */
    @POST("lg/todo/add/json")
    Observable<HttpBaseResponse<Object>> addToDoData(@Query("title") String title, @Query("content") String content,
                                                     @Query("date") String date,
                                                     @Query("type") int type, @Query("priority") int priority);


    /**
     * ToDo列表
     * 方法：POST
     *
     * @param page     页码
     * @param type     类别
     * @param priority 优先级
     * @return
     */
    @POST("lg/todo/v2/list/{page}/json")
    Observable<HttpBaseResponse<ToDoListBean>> getToDoList(@Path("page") int page,
                                                           @Query("type") int type, @Query("priority") int priority);

    /**
     * 更新一条待办
     * 方法：POST
     *
     * @param title    标题
     * @param content  内容
     * @param date     时间
     * @param status   状态
     * @param page     页码
     * @param type     类别
     * @param priority 优先级
     * @return
     */
    @FormUrlEncoded
    @POST("lg/todo/update/{id}/json")
    Observable<HttpBaseResponse<ToDoListBean>> updateToDoList(@Path("id") int page,
                                                              @Field("title") String title, @Field("content") String content,
                                                              @Field("date") String date, @Field("status") int status,
                                                              @Field("type") int type, @Field("priority") int priority);

    /**
     * 删除
     * 方法：POST
     *
     * @param id id
     * @return
     */
    @FormUrlEncoded
    @POST("lg/todo/delete/{id}/json")
    Observable<HttpBaseResponse<Object>> deleteToDo(@Path("id") int id);


    /**
     * 获取积分信息
     *
     * @return
     */
    @GET("lg/coin/userinfo/json")
    Observable<HttpBaseResponse<Integral>> getMyIntegral();

    /**
     * 查询积分详情列表
     * 方法：POST
     *
     * @param page id
     * @return
     */
    @GET("lg/coin/list/{page}/json")
    Observable<HttpBaseResponse<CoinBean>> queryCoinList(@Path("page") int page);

    /**
     * 查询积分详情排行榜
     * 方法：POST
     *
     * @param page id
     * @return
     */
    @GET("coin/rank/{page}/json")
    Observable<HttpBaseResponse<CoinRankBean>> queryCoinRank(@Path("page") int page);

}
