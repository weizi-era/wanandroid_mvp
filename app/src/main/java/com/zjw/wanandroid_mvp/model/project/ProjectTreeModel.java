package com.zjw.wanandroid_mvp.model.project;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.TreeBean;
import com.zjw.wanandroid_mvp.contract.project.ProjectTreeContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class ProjectTreeModel extends BaseModel implements ProjectTreeContract.IProjectTreeModel {


    @Inject
    public ProjectTreeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<List<TreeBean>>> getProjectCategory() {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getProjectCategory();
    }
}
