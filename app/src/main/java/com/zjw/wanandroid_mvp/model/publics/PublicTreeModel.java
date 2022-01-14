package com.zjw.wanandroid_mvp.model.publics;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.TreeBean;
import com.zjw.wanandroid_mvp.contract.publics.PublicTreeContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class PublicTreeModel extends BaseModel implements PublicTreeContract.IPublicTreeModel {


    @Inject
    public PublicTreeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<List<TreeBean>>> getPublicCategory() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getPublicCategory();
    }
}
