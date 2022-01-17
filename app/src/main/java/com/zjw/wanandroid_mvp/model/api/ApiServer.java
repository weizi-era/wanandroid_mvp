package com.zjw.wanandroid_mvp.model.api;


import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BannerBean;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.bean.CoinInfo;
import com.zjw.wanandroid_mvp.bean.HotSearchBean;
import com.zjw.wanandroid_mvp.bean.NaviBean;
import com.zjw.wanandroid_mvp.bean.SharedBean;
import com.zjw.wanandroid_mvp.bean.SystemBean;
import com.zjw.wanandroid_mvp.bean.TodoListBean;
import com.zjw.wanandroid_mvp.bean.TreeBean;
import com.zjw.wanandroid_mvp.bean.RankListBean;
import com.zjw.wanandroid_mvp.bean.ScoreListBean;
import com.zjw.wanandroid_mvp.bean.UserBean;
import com.zjw.wanandroid_mvp.bean.WebsiteBean;
import com.zjw.wanandroid_mvp.model.constant.Constant;


import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServer {

    @GET(Constant.BANNER_URL)
    Observable<BaseBean<List<BannerBean>>> loadBanner();

    @GET(Constant.HOME_ARTICLE)
    Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getHomeArticle(@Path("page") int page);

    @GET(Constant.TOP_ARTICLE)
    Observable<BaseBean<List<ArticleBean>>> getTopArticle();

    @POST(Constant.LOGIN)
    Observable<BaseBean<UserBean>> login(@Query("username") String username, @Query("password") String password);

    @GET(Constant.LOGOUT)
    Observable<BaseBean<Object>> logout();

    @GET(Constant.USER_SCORE)
    Observable<BaseBean<CoinInfo>> getUserScore();

    @POST(Constant.REGISTER)
    Observable<BaseBean<UserBean>> register(@Query("username") String username, @Query("password") String password, @Query("repassword") String repassword);

    @GET(Constant.USER_SCORE_LIST)
    Observable<BaseBean<BasePageBean<List<ScoreListBean>>>> getScoreList(@Path("page") int page);

    @GET(Constant.COLLECTION_ARTICLE_LIST)
    Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getArticleList(@Path("page") int page);

    @GET(Constant.COLLECTION_WEBSITE_LIST)
    Observable<BaseBean<List<WebsiteBean>>> getWebsiteList();

    @POST(Constant.COLLECTION_ARTICLE)
    Observable<BaseBean<Object>> collectArticle(@Path("articleId") int articleId);

    @POST(Constant.UN_COLLECTION_ARTICLE)
    Observable<BaseBean<Object>> unCollectArticle(@Path("articleId") int articleId);

    @POST(Constant.UN_MY_COLLECTION)
    Observable<BaseBean<Object>> unMyCollect(@Path("articleId") int articleId, @Query("originId") int originId);

    @POST(Constant.UN_COLLECTION_WEBSITE)
    Observable<BaseBean<Object>> unCollectWebsite( @Query("id") int id);

    @GET(Constant.SQUARE_LIST)
    Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getSquareArticle(@Path("page") int page);

    @GET(Constant.PUBLIC_LIST)
    Observable<BaseBean<List<TreeBean>>> getPublicCategory();

    @GET(Constant.PUBLIC_ITEM_LIST)
    Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getPublicList(@Path("id") int id, @Path("page") int page);

    @POST(Constant.PUBLISH_ARTICLE)
    Observable<BaseBean<Object>> publishArticle(@Query("title") String title, @Query("link") String link);

    @GET(Constant.PROJECT_TREE)
    Observable<BaseBean<List<TreeBean>>> getProjectCategory();

    @GET(Constant.PROJECT_LIST)
    Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getProjectList(@Path("page") int page, @Query("cid") int cid);

    @GET(Constant.SYSTEM_TREE)
    Observable<BaseBean<List<SystemBean>>> getSystemCategory();

    @GET(Constant.SYSTEM_LIST)
    Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getSystemArticleList(@Path("page") int page, @Query("cid") int cid);

    @GET(Constant.NAVI_TREE)
    Observable<BaseBean<List<NaviBean>>> getNaviArticleList();

    @GET(Constant.QUESTION)
    Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getQuestionList(@Path("page") int page);

    @GET(Constant.MY_SHARED)
    Observable<BaseBean<SharedBean>> getMySharedList(@Path("page") int page);

    @GET(Constant.RANK_LIST)
    Observable<BaseBean<BasePageBean<List<RankListBean>>>> getRankList(@Path("page") int page);

    @GET(Constant.OTHER_SHARED_LIST)
    Observable<BaseBean<SharedBean>> getOtherSharedList(@Path("page") int page, @Path("id") int id);

    @POST(Constant.DELETE_SHARED_ARTICLE)
    Observable<BaseBean<Object>> deleteSharedArticle(@Path("articleId") int articleId);

    @GET(Constant.HOT_SEARCH)
    Observable<BaseBean<List<HotSearchBean>>> getHotSearch();

    @POST(Constant.SEARCH_RESULT)
    Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getSearchResultList(@Path("page") int page, @Query("k") String key);

    @GET(Constant.TODO_LIST)
    Observable<BaseBean<BasePageBean<List<TodoListBean>>>> getTodoList(@Path("page") int page);

    @POST(Constant.DELETE_TODO)
    Observable<BaseBean<Object>> deleteTodo(@Path("id") int id);

    @POST(Constant.ADD_TODO)
    Observable<BaseBean<TodoListBean>> addTodo(@Query("title") String title, @Query("content") String content,
                                         @Query("date") String date, @Query("type") int type, @Query("priority") int priority);
}
