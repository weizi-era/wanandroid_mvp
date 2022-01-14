package com.zjw.wanandroid_mvp.model.collect;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.WebsiteBean;
import com.zjw.wanandroid_mvp.contract.collect.CollectWebsiteContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class CollectWebsiteModel extends BaseModel implements CollectWebsiteContract.ICollectWebsiteModel {

    @Inject
    public CollectWebsiteModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<List<WebsiteBean>>> getWebsiteList() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getWebsiteList();
    }

    @Override
    public Observable<BaseBean<Object>> unCollect(int articleId) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).unCollectWebsite(articleId);
    }
}
