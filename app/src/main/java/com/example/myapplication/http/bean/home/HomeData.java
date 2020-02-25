package com.example.myapplication.http.data;

import com.example.myapplication.http.bean.HomeBanner;

/**
 * @author : devel
 * @date : 2020/2/25 13:27
 * @desc :
 */
public class HomeData {

    private HomeBanner bannerData;
    private ArticleList articleList;

    public HomeBanner getBannerData() {
        return bannerData;
    }

    public void setBannerData(HomeBanner bannerData) {
        this.bannerData = bannerData;
    }

    public ArticleList getArticleList() {
        return articleList;
    }

    public void setArticleList(ArticleList articleList) {
        this.articleList = articleList;
    }


}
