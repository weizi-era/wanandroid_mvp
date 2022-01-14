package com.zjw.wanandroid_mvp.di.module.project;

import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.contract.collect.CollectArticleContract;
import com.zjw.wanandroid_mvp.contract.project.ProjectListContract;
import com.zjw.wanandroid_mvp.model.collect.CollectArticleModel;
import com.zjw.wanandroid_mvp.model.project.ProjectListModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class ProjectListModule {

    private final ProjectListContract.IProjectListView view;

    public ProjectListModule(@NotNull ProjectListContract.IProjectListView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public final ProjectListContract.IProjectListView provideProjectListView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    public final ProjectListContract.IProjectListModel provideProjectListModel(ProjectListModel model) {
        return model;
    }

}
