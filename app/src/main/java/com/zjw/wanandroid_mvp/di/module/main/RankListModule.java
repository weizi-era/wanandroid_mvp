package com.zjw.wanandroid_mvp.di.module.main;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.contract.main.RankListContract;
import com.zjw.wanandroid_mvp.model.main.RankListModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class RankListModule {

    private final RankListContract.IRankListView view;

    public RankListModule(@NotNull RankListContract.IRankListView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public final RankListContract.IRankListView provideRankListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public final RankListContract.IRankListModel provideRankListModel(RankListModel model) {
        return model;
    }
}
