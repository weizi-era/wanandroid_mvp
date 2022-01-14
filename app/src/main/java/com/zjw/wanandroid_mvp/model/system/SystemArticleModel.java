package com.zjw.wanandroid_mvp.model.system;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.contract.system.SystemArticleContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class SystemArticleModel extends BaseModel implements SystemArticleContract.ISystemArticleModel {

    @Inject
    public SystemArticleModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getArticleList(int page, int cid) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getSystemArticleList(page, cid);
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
