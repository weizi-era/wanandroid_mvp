package com.zjw.wanandroid_mvp.di.module.project;

import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.contract.project.ProjectListContract;
import com.zjw.wanandroid_mvp.contract.project.ProjectTreeContract;
import com.zjw.wanandroid_mvp.model.project.ProjectListModel;
import com.zjw.wanandroid_mvp.model.project.ProjectTreeModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class ProjectTreeModule {

    private final ProjectTreeContract.IProjectTreeView view;

    public ProjectTreeModule(@NotNull ProjectTreeContract.IProjectTreeView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public final ProjectTreeContract.IProjectTreeView provideProjectTreeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    public final ProjectTreeContract.IProjectTreeModel provideProjectTreeModel(ProjectTreeModel model) {
        return model;
    }

}
