package com.zjw.wanandroid_mvp.model.home.search;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.HotSearchBean;
import com.zjw.wanandroid_mvp.contract.home.search.SearchContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class HotSearchModel extends BaseModel implements SearchContract.ISearchModel {

    @Inject
    public HotSearchModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<List<HotSearchBean>>> getHotSearch() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getHotSearch();
    }
}
