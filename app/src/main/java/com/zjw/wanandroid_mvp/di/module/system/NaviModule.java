package com.zjw.wanandroid_mvp.di.module.system;

import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.contract.collect.CollectArticleContract;
import com.zjw.wanandroid_mvp.contract.system.NaviContract;
import com.zjw.wanandroid_mvp.model.collect.CollectArticleModel;
import com.zjw.wanandroid_mvp.model.system.NaviListModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class NaviModule {

    private final NaviContract.INaviView view;

    public NaviModule(@NotNull NaviContract.INaviView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public final NaviContract.INaviView provideNaviView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    public final NaviContract.INaviModel provideNaviModel(NaviListModel model) {
        return model;
    }

}
