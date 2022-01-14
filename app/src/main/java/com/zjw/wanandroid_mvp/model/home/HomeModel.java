package com.zjw.wanandroid_mvp.model.home;


import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BannerBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.contract.home.HomeContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class HomeModel extends BaseModel implements HomeContract.IHomeModel {


    @Inject
    public HomeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<List<BannerBean>>> loadBanner() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).loadBanner();
    }

    @Override
    public Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getHomeArticle(int page) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getHomeArticle(page);
    }

    @Override
    public Observable<BaseBean<List<ArticleBean>>> getTopArticle() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getTopArticle();
    }

    @Override
    public Observable<BaseBean<Object>> collect(int articleId) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).collectArticle(articleId);
    }

    @Override
    public Observable<BaseBean<Object>> unCollect(int articleId) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).unCollectArticle(articleId);
    }
}
