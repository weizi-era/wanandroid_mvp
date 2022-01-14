package com.zjw.wanandroid_mvp.model.main;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.bean.RankListBean;
import com.zjw.wanandroid_mvp.contract.main.RankListContract;
import com.zjw.wanandroid_mvp.model.api.ApiServer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class RankListModel extends BaseModel implements RankListContract.IRankListModel {


    @Inject
    public RankListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<BasePageBean<List<RankListBean>>>> getRankList(int page) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getRankList(page);
    }
}
