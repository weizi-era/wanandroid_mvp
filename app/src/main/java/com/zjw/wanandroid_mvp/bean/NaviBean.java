package com.zjw.wanandroid_mvp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NaviBean {

    @SerializedName("cid")
    private int cid;
    @SerializedName("name")
    private String name;
    @SerializedName("articles")
    private List<ArticleBean> articles;

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
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
