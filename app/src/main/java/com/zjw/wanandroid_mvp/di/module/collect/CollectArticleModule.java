package com.zjw.wanandroid_mvp.di.module.collect;

import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.contract.collect.CollectArticleContract;
import com.zjw.wanandroid_mvp.model.collect.CollectArticleModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class CollectArticleModule {

    private final CollectArticleContract.ICollectArticleView view;

    public CollectArticleModule(@NotNull CollectArticleContract.ICollectArticleView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public final CollectArticleContract.ICollectArticleView provideCollectArticleView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    public final CollectArticleContract.ICollectArticleModel provideCollectArticleModel(CollectArticleModel model) {
        return model;
    }

}
