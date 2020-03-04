package com.example.myapplication.bean.responsebean;

import java.util.List;

/**
 * @author devel
 * 导航信息
 */
public class NavigationBean {

    private Integer cid;
    private String name;
    private List<ArticleBean> articles;

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ArticleBean> getArticles() {
        return articles;
    }

    public void setArticles(List<ArticleBean> articles) {
        this.articles = articles;
    }
}
