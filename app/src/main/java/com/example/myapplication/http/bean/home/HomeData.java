package com.example.myapplication.http.bean.home;

import com.example.myapplication.http.bean.ArticleBean;

/**
 * @author : devel
 * @date : 2020/2/25 13:27
 * @desc :
 */
public class HomeData {

    private BannerData bannerData;
    private ArticleBean articleList;

    public BannerData getBannerData() {
        return bannerData;
    }

    public void setBannerData(BannerData bannerData) {
        this.bannerData = bannerData;
    }

    public ArticleBean getArticleList() {
        return articleList;
    }

    public void setArticleList(ArticleBean articleList) {
        this.articleList = articleList;
    }


}
