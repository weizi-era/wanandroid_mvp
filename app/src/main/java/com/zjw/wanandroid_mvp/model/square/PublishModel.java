package com.zjw.wanandroid_mvp.model.square;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.contract.square.PublishContract;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class PublishModel extends BaseModel implements PublishContract.IPublishModel {

    @Inject
    public PublishModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<Object>> addArticle(String title, String link) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).publishArticle(title, link);
    }
}
