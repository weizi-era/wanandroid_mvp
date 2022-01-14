package com.zjw.wanandroid_mvp.di.module.publics;

import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.contract.project.ProjectListContract;
import com.zjw.wanandroid_mvp.contract.publics.PublicListContract;
import com.zjw.wanandroid_mvp.model.project.ProjectListModel;
import com.zjw.wanandroid_mvp.model.publics.PublicListModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class PublicListModule {

    private final PublicListContract.IPublicListView view;

    public PublicListModule(@NotNull PublicListContract.IPublicListView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public final PublicListContract.IPublicListView providePublicListView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    public final PublicListContract.IPublicListModel providePublicListModel(PublicListModel model) {
        return model;
    }

}
