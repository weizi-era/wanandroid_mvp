package com.zjw.wanandroid_mvp.model.share;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.SharedBean;
import com.zjw.wanandroid_mvp.contract.share.OtherShareContract;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class OtherShareModel extends BaseModel implements OtherShareContract.IOtherShareModel {

    @Inject
    public OtherShareModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<SharedBean>> getArticleList(int page, int id) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getOtherSharedList(page, id);
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
