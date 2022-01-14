package com.zjw.wanandroid_mvp.di.module.share;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.contract.share.OtherShareContract;
import com.zjw.wanandroid_mvp.model.share.OtherShareModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class OtherSharedModule {

    private final OtherShareContract.IOtherShareView view;

    public OtherSharedModule(@NotNull OtherShareContract.IOtherShareView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public final OtherShareContract.IOtherShareView provideOtherShardView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public final OtherShareContract.IOtherShareModel provideOtherSharedModel(OtherShareModel model) {
        return model;
    }
}
