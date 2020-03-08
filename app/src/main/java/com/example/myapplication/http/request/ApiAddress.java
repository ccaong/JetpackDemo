package com.example.myapplication.http.request;

import com.example.myapplication.bean.responsebean.ArticleBean;
import com.example.myapplication.bean.responsebean.ArticleListBean;
import com.example.myapplication.bean.responsebean.Coin;
import com.example.myapplication.bean.responsebean.CoinBean;
import com.example.myapplication.bean.responsebean.CoinRankBean;
import com.example.myapplication.bean.responsebean.CollectArticleBean;
import com.example.myapplication.bean.responsebean.HomeBanner;
import com.example.myapplication.bean.responsebean.ImageBean;
import com.example.myapplication.bean.responsebean.LoginBean;
import com.example.myapplication.bean.responsebean.NavigationBean;
import com.example.myapplication.bean.responsebean.ToDoListBean;
import com.example.myapplication.bean.responsebean.UserShareArticleBean;
import com.example.myapplication.bean.responsebean.WeChatBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Response;
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
    Observable<LoginBean> Login(@Field("username") String username, @Field("password") String password);

    /**
     * 退出
     *
     * @return
     */
    @GET("user/logout/json")
    Observable<Response<Void>> logout();


    /**
     * 注册
     *
     * @return
     */
    @FormUrlEncoded
    @POST("user/register")
    Observable<Object> register(@Field("username") String username,
                                @Field("password") String password,
                                @Field("repassword") String repwd);


    /**
     * 获取首页banner
     *
     * @return
     */
    @GET("banner/json")
    Observable<List<HomeBanner>> getBanner();


    /**
     * 获取置顶文章
     *
     * @return
     */
    @GET("article/top/json")
    Observable<List<ArticleBean>> getTopArticleList();

    /**
     * 首页文章列表
     * 方法：GET
     *
     * @param page 页码
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<ArticleListBean> getArticleList(@Path("page") int page);

    /**
     * 获取体系列表
     *
     * @return
     */
    @GET("tree/json")
    Observable<List<WeChatBean>> getSystemList();


    /**
     * 获取体系文章列表
     *
     * @param cid  id
     * @param page 页码
     * @return
     */
    @GET("article/list/{page}/json")
    Observable<ArticleListBean> getSystemArticle(@Path("page") int page, @Query("cid") int cid);

    /**
     * 获取项目文章列表
     *
     * @param cid  id
     * @param page 页码
     * @return
     */
    @GET("project/list/{page}/json")
    Observable<ArticleListBean> getProjectArticle(@Path("page") int page, @Query("cid") int cid);


    /**
     * 获取微信公众号列表
     *
     * @return
     */
    @GET("wxarticle/chapters/json")
    Observable<List<WeChatBean>> getWechatList();


    /**
     * 获取微信公众号文章列表
     *
     * @param id   id
     * @param page 页码
     * @return
     */
    @GET("wxarticle/list/{id}/{page}/json")
    Observable<ArticleListBean> getWechatArticleList(@Path("id") int id, @Path("page") int page);

    /**
     * 获取广场文章列表
     *
     * @param page 页码
     * @return
     */
    @GET("user_article/list/{page}/json")
    Observable<ArticleListBean> getSquareArticleList(@Path("page") int page);

    /**
     * 获取用户分享的文章列表
     *
     * @param page 页码
     * @return
     */
    @GET("/user/{id}/share_articles/{page}/json")
    Observable<UserShareArticleBean> getUserShareArticle(@Path("id") int id, @Path("page") int page);


    /**
     * 获取导航列表
     *
     * @return
     */
    @GET("navi/json")
    Observable<List<NavigationBean>> getNavigationBean();


    /**
     * 获取项目分类列表数据
     *
     * @return 项目分类列表数据
     */
    @GET("project/tree/json")
    Observable<List<WeChatBean>> getProjectListData();


    /**
     * 获取收藏的文章
     *
     * @param page 页码
     * @return
     */
    @GET("lg/collect/list/{page}/json")
    Observable<CollectArticleBean> getCollectList(@Path("page") int page);

    /**
     * 收藏文章
     *
     * @param id 要收藏的文章id
     * @return
     */
    @POST("lg/collect/{id}/json")
    Observable<Response<Void>> collectArticle(@Path("id") int id);

    /**
     * 在文章界面取消收藏文章
     *
     * @param id 要取消收藏的文章id
     * @return
     */
    @POST("lg/uncollect_originId/{id}/json")
    Observable<Object> unCollectArticle(@Path("id") int id);

    /**
     * 在收藏界面取消收藏文章
     *
     * @param id       要取消收藏的文章id
     * @param originId 原始id
     * @return
     */
    @POST("lg/uncollect/{id}/json")
    Observable<Response<Void>> unCollect(@Path("id") int id, @Query("originId") int originId);


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
    Observable<Object> addToDoData(@Query("title") String title, @Query("content") String content,
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
    Observable<ToDoListBean> getToDoList(@Path("page") int page,
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
    Observable<Object> updateToDoList(@Path("id") int page,
                                      @Field("title") String title, @Field("content") String content,
                                      @Field("date") String date, @Field("status") int status,
                                      @Field("type") int type, @Field("priority") int priority);

    /**
     * 删除一条todo
     * 方法：POST
     *
     * @param id id
     * @return
     */
    @POST("lg/todo/delete/{id}/json")
    Observable<Response<Void>> deleteToDo(@Path("id") int id);


    /**
     * 获取积分信息
     *
     * @return
     */
    @GET("lg/coin/userinfo/json")
    Observable<Coin> getMyIntegral();

    /**
     * 查询积分详情列表
     * 方法：POST
     *
     * @param page id
     * @return
     */
    @GET("lg/coin/list/{page}/json")
    Observable<CoinBean> queryCoinList(@Path("page") int page);

    /**
     * 查询积分详情排行榜
     * 方法：GET
     *
     * @param page id
     * @return
     */
    @GET("coin/rank/{page}/json")
    Observable<CoinRankBean> queryCoinRank(@Path("page") int page);

    /**
     * 查询问答列表
     * 方法：GET
     *
     * @param page id
     * @return
     */
    @GET("wenda/list/{page}/json ")
    Observable<ArticleListBean> queryQaArtistList(@Path("page") int page);

}
