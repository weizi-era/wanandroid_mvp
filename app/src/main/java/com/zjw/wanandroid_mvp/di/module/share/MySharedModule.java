package com.zjw.wanandroid_mvp.di.module.share;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.contract.share.MyShareContract;
import com.zjw.wanandroid_mvp.model.share.MyShareModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class MySharedModule {

    private final MyShareContract.IMyShareView view;

    public MySharedModule(@NotNull MyShareContract.IMyShareView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public final MyShareContract.IMyShareView provideMyShardView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public final MyShareContract.IMyShareModel provideMySharedModel(MyShareModel model) {
        return model;
    }
}
