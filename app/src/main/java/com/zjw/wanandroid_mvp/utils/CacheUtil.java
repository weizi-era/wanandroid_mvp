package com.zjw.wanandroid_mvp.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.tencent.mmkv.MMKV;
import com.zjw.wanandroid_mvp.bean.CoinInfo;
import com.zjw.wanandroid_mvp.bean.HotSearchBean;
import com.zjw.wanandroid_mvp.bean.TreeBean;
import com.zjw.wanandroid_mvp.bean.UserBean;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class CacheUtil {

    public static UserBean getUserInfo() {
        MMKV mmkv = MMKV.mmkvWithID("app");
        String user = mmkv.decodeString("user");
        Type type = new TypeToken<UserBean>() {}.getType();
        if (!TextUtils.isEmpty(user)) {
            return new Gson().fromJson(user, type);
        }
        return null;
    }

    public static void setUserInfo(UserBean bean) {
        MMKV mmkv = MMKV.mmkvWithID("app");
        if (bean == null) {
            mmkv.encode("user", "");
            setCookie("");
            setIsLogin(false);
        } else {
            mmkv.encode("user", new Gson().toJson(bean));
            setIsLogin(true);
        }
    }

    public static CoinInfo getScoreInfo() {
        MMKV mmkv = MMKV.mmkvWithID("app");
        String score = mmkv.decodeString("score");
        Type type = new TypeToken<CoinInfo>() {}.getType();
        if (!TextUtils.isEmpty(score)) {
            return new Gson().fromJson(score, type);
        }
        return null;
    }

    public static void setScoreInfo(CoinInfo bean) {
        MMKV mmkv = MMKV.mmkvWithID("app");
        if (bean == null) {
            mmkv.encode("score", "");
            setCookie("");
            setIsLogin(false);
        } else {
            mmkv.encode("score", new Gson().toJson(bean));
            setIsLogin(true);
        }
    }

    public static boolean isLogin() {
        MMKV mmkv = MMKV.mmkvWithID("app");
        return mmkv.decodeBool("login", false);
    }

    public static void setIsLogin(boolean isLogin) {
        MMKV mmkv = MMKV.mmkvWithID("app");
        mmkv.encode("login", isLogin);
    }

    public static void setCookie(String cookie) {
        MMKV mmkv = MMKV.mmkvWithID("app");
        mmkv.encode("cookie", cookie);
    }

    public static String getCookie() {
        MMKV mmkv = MMKV.mmkvWithID("app");
        return mmkv.decodeString("cookie");
    }

    public static List<TreeBean> getPublicTagCache() {
        MMKV mmkv = MMKV.mmkvWithID("cache");
        String publicTag = mmkv.decodeString("public_tag");
        Type type = new TypeToken<List<TreeBean>>() {}.getType();
        if (!TextUtils.isEmpty(publicTag)) {
            return new Gson().fromJson(publicTag, type);
        }
        return new ArrayList<>();
    }

    public static void setPublicTagCache(String publicTag) {
        MMKV mmkv = MMKV.mmkvWithID("cache");
        mmkv.encode("public_tag", publicTag);
    }


    public static List<TreeBean> getProjectTagCache() {
        MMKV mmkv = MMKV.mmkvWithID("cache");
        String projectTag = mmkv.decodeString("project_tag");
        Type type = new TypeToken<List<TreeBean>>() {}.getType();
        if (!TextUtils.isEmpty(projectTag)) {
            return new Gson().fromJson(projectTag, type);
        }
        return new ArrayList<>();
    }

    public static void setProjectTagCache(String projectTag) {
        MMKV mmkv = MMKV.mmkvWithID("cache");
        mmkv.encode("project_tag", projectTag);
    }


    /**
     * 获取 搜索热词 缓存
     * @return
     */
    public static List<HotSearchBean> getHotSearchCache() {

        MMKV mmkv = MMKV.mmkvWithID("cache");
        String search = mmkv.decodeString("search");
        Type type = new TypeToken<List<HotSearchBean>>() {}.getType();
        if (!TextUtils.isEmpty(search)) {
            return new Gson().fromJson(search, type);
        }

        return new ArrayList<>();

    }

    /**
     * 设置搜索热词到缓存
     * @param search
     */
    public static void setHotSearchCache(String search) {
        MMKV mmkv = MMKV.mmkvWithID("cache");
        mmkv.encode("search", search);
    }

    /**
     * 获取 历史搜索 缓存
     * @return
     */
    public static List<String> getHistorySearchCache() {

        MMKV mmkv = MMKV.mmkvWithID("cache");
        String historySearch = mmkv.decodeString("history");
        Type type = new TypeToken<List<String>>() {}.getType();
        return new Gson().fromJson(historySearch, type);
    }

    /**
     * 设置历史搜索到缓存
     * @param historySearch
     */
    public static void setHistorySearchCache(String historySearch) {
        MMKV mmkv = MMKV.mmkvWithID("cache");
        mmkv.encode("history", historySearch);
    }
}
