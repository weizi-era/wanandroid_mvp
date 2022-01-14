package com.zjw.wanandroid_mvp.model.system;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.bean.SystemBean;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.TreeBean;
import com.zjw.wanandroid_mvp.contract.system.SystemContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class SystemModel extends BaseModel implements SystemContract.ISystemModel {


    @Inject
    public SystemModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<List<SystemBean>>> getSystemList() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getSystemCategory();
    }
}
