package com.zjw.wanandroid_mvp.di.module.main;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.contract.main.IntegralContract;
import com.zjw.wanandroid_mvp.model.main.IntegralModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class IntegralModule {

    private final IntegralContract.IIntegralView view;

    public IntegralModule(@NotNull IntegralContract.IIntegralView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public final IntegralContract.IIntegralView provideIntegralView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public final IntegralContract.IIntegralModel provideIntegralModel(IntegralModel model) {
        return model;
    }
}
