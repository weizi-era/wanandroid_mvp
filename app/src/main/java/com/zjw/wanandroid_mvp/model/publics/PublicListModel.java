package com.zjw.wanandroid_mvp.model.publics;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.contract.publics.PublicListContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class PublicListModel extends BaseModel implements PublicListContract.IPublicListModel {


    @Inject
    public PublicListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getPublicList(int id, int page) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getPublicList(id, page);
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
