package com.zjw.wanandroid_mvp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ScoreListBean {

        @SerializedName("coinCount")
        private int coinCount;
        @SerializedName("date")
        private long date;
        @SerializedName("desc")
        private String desc;
        @SerializedName("id")
        private int id;
        @SerializedName("reason")
        private String reason;
        @SerializedName("type")
        private int type;
        @SerializedName("userId")
        private int userId;
        @SerializedName("userName")
        private String userName;

        public int getCoinCount() {
            return coinCount;
        }

        public void setCoinCount(int coinCount) {
            this.coinCount = coinCount;
        }

        public long getDate() {
            return date;
        }

        public void setDate(long date) {
            this.date = date;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
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

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

}
