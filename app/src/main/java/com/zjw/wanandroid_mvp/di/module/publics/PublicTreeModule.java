package com.zjw.wanandroid_mvp.di.module.publics;

import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.contract.publics.PublicListContract;
import com.zjw.wanandroid_mvp.contract.publics.PublicTreeContract;
import com.zjw.wanandroid_mvp.model.publics.PublicListModel;
import com.zjw.wanandroid_mvp.model.publics.PublicTreeModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class PublicTreeModule {

    private final PublicTreeContract.IPublicTreeView view;

    public PublicTreeModule(@NotNull PublicTreeContract.IPublicTreeView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public final PublicTreeContract.IPublicTreeView providePublicTreeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    public final PublicTreeContract.IPublicTreeModel providePublicTreeModel(PublicTreeModel model) {
        return model;
    }

}
