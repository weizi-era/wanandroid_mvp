package com.zjw.wanandroid_mvp.di.module.system;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.contract.system.SystemArticleContract;
import com.zjw.wanandroid_mvp.model.system.SystemArticleModel;
import com.zjw.wanandroid_mvp.model.system.SystemModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class SystemArticleModule {

    private final SystemArticleContract.ISystemArticleView view;

    public SystemArticleModule(@NotNull SystemArticleContract.ISystemArticleView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public final SystemArticleContract.ISystemArticleView provideSystemArticleView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public final SystemArticleContract.ISystemArticleModel provideSystemArticleModel(SystemArticleModel model) {
        return model;
    }

}
