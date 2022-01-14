package com.zjw.wanandroid_mvp.di.module.main;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.contract.main.MainContract;
import com.zjw.wanandroid_mvp.model.main.MainModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {

    private final MainContract.IMainView view;

    public MainModule(@NotNull MainContract.IMainView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public final MainContract.IMainView provideMainView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public final MainContract.IMainModel provideMainModel(MainModel model) {
        return model;
    }
}
