package com.zjw.wanandroid_mvp.bean;

import com.google.gson.annotations.SerializedName;

public class CoinInfo {

    @SerializedName("coinCount")
    private int coinCount;
    @SerializedName("level")
    private int level;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("rank")
    private String rank;
    @SerializedName("userId")
    private int userId;
    @SerializedName("username")
    private String username;

    public int getCoinCount() {
        return coinCount;
    }

    public void setCoinCount(int coinCount) {
        this.coinCount = coinCount;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
