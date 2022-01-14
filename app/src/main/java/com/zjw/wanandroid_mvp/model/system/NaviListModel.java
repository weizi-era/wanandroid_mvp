package com.zjw.wanandroid_mvp.model.system;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.NaviBean;
import com.zjw.wanandroid_mvp.contract.system.NaviContract;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class NaviListModel extends BaseModel implements NaviContract.INaviModel {


    @Inject
    public NaviListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<List<NaviBean>>> getNaviArticleList() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getNaviArticleList();
    }
}
