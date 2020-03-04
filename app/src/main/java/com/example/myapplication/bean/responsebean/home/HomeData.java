package com.example.myapplication.bean.responsebean.home;

import com.example.myapplication.bean.responsebean.ArticleBean;

import java.util.List;

/**
 * @author : devel
 * @date : 2020/2/25 13:27
 * @desc :
 */
public class HomeData {

    private BannerData bannerData;
    private TopArticle topArticleList;
    private ArticleBean articleList;

    public BannerData getBannerData() {
        return bannerData;
    }

    public void setBannerData(BannerData bannerData) {
        this.bannerData = bannerData;
    }

    public void setTopArticleList(TopArticle topArticleList) {
        this.topArticleList = topArticleList;
    }

    public TopArticle getTopArticleList() {
        return topArticleList;
    }

    public ArticleBean getArticleList() {
        return articleList;
    }

    public void setArticleList(ArticleBean articleList) {
        this.articleList = articleList;
    }


    public static class TopArticle {
        private String name;

        private List<ArticleBean> articleBeanList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ArticleBean> getArticleBeanList() {
            return articleBeanList;
        }

        public void setArticleBeanList(List<ArticleBean> articleBeanList) {
            this.articleBeanList = articleBeanList;
        }
    }
}
