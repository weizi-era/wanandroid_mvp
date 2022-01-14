package com.zjw.wanandroid_mvp.di.module.system;

import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.contract.system.NaviContract;
import com.zjw.wanandroid_mvp.contract.system.SystemContract;
import com.zjw.wanandroid_mvp.model.system.NaviListModel;
import com.zjw.wanandroid_mvp.model.system.SystemModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class SystemModule {

    private final SystemContract.ISystemView view;

    public SystemModule(@NotNull SystemContract.ISystemView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public final SystemContract.ISystemView provideSystemView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    public final SystemContract.ISystemModel provideSystemModel(SystemModel model) {
        return model;
    }

}
