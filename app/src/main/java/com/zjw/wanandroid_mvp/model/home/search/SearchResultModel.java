package com.zjw.wanandroid_mvp.model.home.search;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.contract.home.search.SearchResultContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class SearchResultModel extends BaseModel implements SearchResultContract.ISearchResultModel {


    @Inject
    public SearchResultModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getSearchResultList(int page, String key) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getSearchResultList(page, key);
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
