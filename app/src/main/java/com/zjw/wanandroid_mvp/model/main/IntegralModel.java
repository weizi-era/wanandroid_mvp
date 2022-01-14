package com.zjw.wanandroid_mvp.model.main;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.bean.ScoreListBean;
import com.zjw.wanandroid_mvp.contract.main.IntegralContract;
import com.zjw.wanandroid_mvp.model.api.ApiServer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class IntegralModel extends BaseModel implements IntegralContract.IIntegralModel {


    @Inject
    public IntegralModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<BasePageBean<List<ScoreListBean>>>> getScoreList(int page) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getScoreList(page);
    }
}
