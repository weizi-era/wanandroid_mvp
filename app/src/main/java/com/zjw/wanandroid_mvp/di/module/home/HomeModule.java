package com.zjw.wanandroid_mvp.di.module.home;

import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.contract.collect.CollectArticleContract;
import com.zjw.wanandroid_mvp.contract.home.HomeContract;
import com.zjw.wanandroid_mvp.model.collect.CollectArticleModel;
import com.zjw.wanandroid_mvp.model.home.HomeModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    private final HomeContract.IHomeView view;

    public HomeModule(@NotNull HomeContract.IHomeView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public final HomeContract.IHomeView provideHomeView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    public final HomeContract.IHomeModel provideHomeModel(HomeModel model) {
        return model;
    }

}
