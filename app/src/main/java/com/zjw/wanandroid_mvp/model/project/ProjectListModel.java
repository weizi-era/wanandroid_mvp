package com.zjw.wanandroid_mvp.model.project;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.contract.project.ProjectListContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class ProjectListModel extends BaseModel implements ProjectListContract.IProjectListModel {


    @Inject
    public ProjectListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getProjectList(int page, int cid) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getProjectList(page, cid);
    }

    @Override
    public Observable<BaseBean<Object>> collect(int articleId) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).collectArticle(articleId);
    }

    @Override
    public Observable<BaseBean<Object>> unCollect(int articleId) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).unCollectArticle(articleId);
    }
}
