package com.zjw.wanandroid_mvp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HotSearchBean  {

    @SerializedName("id")
    private int id;
    @SerializedName("link")
    private String link;
    @SerializedName("name")
    private String name;
    @SerializedName("order")
    private int order;
    @SerializedName("visible")
    private int visible;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
