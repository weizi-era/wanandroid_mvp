package com.zjw.wanandroid_mvp.model.share;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.SharedBean;
import com.zjw.wanandroid_mvp.contract.share.MyShareContract;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class MyShareModel extends BaseModel implements MyShareContract.IMyShareModel {

    @Inject
    public MyShareModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<SharedBean>> getArticleList(int page) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getMySharedList(page);
    }

    @Override
    public Observable<BaseBean<Object>> deleteArticle(int articleId) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).deleteSharedArticle(articleId);
    }
}
