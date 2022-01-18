package com.zjw.wanandroid_mvp.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class TodoBean implements Serializable {

    @SerializedName("completeDate")
    private Object completeDate;
    @SerializedName("completeDateStr")
    private String completeDateStr;
    @SerializedName("content")
    private String content;
    @SerializedName("date")
    private long date;
    @SerializedName("dateStr")
    private String dateStr;
    @SerializedName("id")
    private int id;
    @SerializedName("priority")
    private int priority;
    @SerializedName("status")
    private int status;
    @SerializedName("title")
    private String title;
    @SerializedName("type")
    private int type;
    @SerializedName("userId")
    private int userId;

    public Object getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(Object completeDate) {
        this.completeDate = completeDate;
    }

    public String getCompleteDateStr() {
        return completeDateStr;
    }

    public void setCompleteDateStr(String completeDateStr) {
        this.completeDateStr = completeDateStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
