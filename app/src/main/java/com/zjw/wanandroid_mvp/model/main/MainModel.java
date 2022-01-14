package com.zjw.wanandroid_mvp.model.main;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.CoinInfo;
import com.zjw.wanandroid_mvp.contract.main.MainContract;
import com.zjw.wanandroid_mvp.model.api.ApiServer;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class MainModel extends BaseModel implements MainContract.IMainModel {

    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<CoinInfo>> loadUserInfo() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getUserScore();
    }

    @Override
    public Observable<BaseBean<Object>> logout() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).logout();
    }
}
