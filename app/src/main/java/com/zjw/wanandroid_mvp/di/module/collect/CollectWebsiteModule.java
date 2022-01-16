package com.zjw.wanandroid_mvp.di.module.collect;

import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.contract.collect.CollectArticleContract;
import com.zjw.wanandroid_mvp.contract.collect.CollectWebsiteContract;
import com.zjw.wanandroid_mvp.model.collect.CollectArticleModel;
import com.zjw.wanandroid_mvp.model.collect.CollectWebsiteModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class CollectWebsiteModule {

    private final CollectWebsiteContract.ICollectWebsiteView view;

    public CollectWebsiteModule(@NotNull CollectWebsiteContract.ICollectWebsiteView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public final CollectWebsiteContract.ICollectWebsiteView provideCollectWebsiteView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    public final CollectWebsiteContract.ICollectWebsiteModel provideCollectWebsiteModel(CollectWebsiteModel model) {
        return model;
    }
}
