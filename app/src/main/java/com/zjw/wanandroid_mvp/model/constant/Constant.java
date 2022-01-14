package com.zjw.wanandroid_mvp.model.constant;

public class Constant {

    public static final String BASE_URL = "https://www.wanandroid.com/";

    public static final String SCORE_RULE_URL = "https://www.wanandroid.com/blog/show/2653";

    public static final String BANNER_URL = "banner/json";

    public static final String HOME_ARTICLE = "article/list/{page}/json";

    public static final String LOGIN = "user/login";

    public static final String REGISTER = "user/register";

    public static final String LOGOUT = "user/logout/json";

    public static final String USER_SCORE = "lg/coin/userinfo/json";

    public static final String USER_SCORE_LIST = "lg/coin/list/{page}/json";

    public static final String COLLECTION_ARTICLE_LIST = "lg/collect/list/{page}/json";

    public static final String COLLECTION_WEBSITE_LIST = "lg/collect/usertools/json";

    public static final String COLLECTION_ARTICLE = "lg/collect/{articleId}/json";

    public static final String UN_COLLECTION_ARTICLE = "lg/uncollect_originId/{articleId}/json";

    public static final String TOP_ARTICLE = "article/top/json";

    public static final String SQUARE_LIST = "user_article/list/{page}/json";

    public static final String PUBLIC_LIST = "wxarticle/chapters/json";

    public static final String PUBLIC_ITEM_LIST = "wxarticle/list/{id}/{page}/json";

    public static final String PUBLISH_ARTICLE = "lg/user_article/add/json";

    /**
     * 项目分类
     */
    public static final String PROJECT_TREE = "project/tree/json";

    /**
     * 项目列表数据
     */
    public static final String PROJECT_LIST = "project/list/{page}/json";

    public static final String SYSTEM_TREE = "tree/json";

    public static final String SYSTEM_LIST = "/article/list/{page}/json";

    public static final String NAVI_TREE = "navi/json";

    public static final String QUESTION = "wenda/list/{page}/json";

    public static final String MY_SHARED = "user/lg/private_articles/{page}/json";

    public static final String RANK_LIST = "coin/rank/{page}/json";

    public static final String OTHER_SHARED_LIST = "user/{id}/share_articles/{page}/json";

    public static final String DELETE_SHARED_ARTICLE = "lg/user_article/delete/{articleId}/json";

    public static final String UN_MY_COLLECTION = "lg/uncollect/{articleId}/json";

    public static final String UN_COLLECTION_WEBSITE = "lg/collect/deletetool/json";

    public static final String HOT_SEARCH = "hotkey/json";

    public static final String SEARCH_RESULT = "article/query/{page}/json";

    /**
     * 获取成功
     */
    public static final int SUCCESS = 0;

    public static final int PWD_MIN_LENGTH = 6;

    public static final int OTHER_SHARED_TAG = 1;
    public static final int MY_SHARED_TAG = 2;

    public static final int TYPE_ARTICLE = 1;
    public static final int TYPE_PROJECT = 2;

}

