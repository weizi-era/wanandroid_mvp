package com.zjw.wanandroid_mvp.di.module.square;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.contract.square.PublishContract;
import com.zjw.wanandroid_mvp.contract.square.SquareContract;
import com.zjw.wanandroid_mvp.model.square.PublishModel;
import com.zjw.wanandroid_mvp.model.square.SquareModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class PublishModule {

    private final PublishContract.IPublishView view;

    public PublishModule(@NotNull PublishContract.IPublishView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public final PublishContract.IPublishView providePublishView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public final PublishContract.IPublishModel providePublishModel(PublishModel model) {
        return model;
    }

}
