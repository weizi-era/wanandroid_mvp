package com.zjw.wanandroid_mvp.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SharedBean {

        @SerializedName("coinInfo")
        private CoinInfo coinInfo;
        @SerializedName("shareArticles")
        private BasePageBean<List<ArticleBean>> shareArticles;

        public CoinInfo getCoinInfo() {
            return coinInfo;
        }

        public void setCoinInfo(CoinInfo coinInfo) {
            this.coinInfo = coinInfo;
        }

        public BasePageBean<List<ArticleBean>> getShareArticles() {
            return shareArticles;
        }

        public void setShareArticles(BasePageBean<List<ArticleBean>> shareArticles) {
            this.shareArticles = shareArticles;
        }
}
