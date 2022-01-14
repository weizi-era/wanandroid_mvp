package com.zjw.wanandroid_mvp.di.module.home.search;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.contract.collect.CollectArticleContract;
import com.zjw.wanandroid_mvp.contract.home.search.SearchContract;
import com.zjw.wanandroid_mvp.model.collect.CollectArticleModel;
import com.zjw.wanandroid_mvp.model.home.search.HotSearchModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {

    private final SearchContract.ISearchView view;

    public SearchModule(@NotNull SearchContract.ISearchView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public final SearchContract.ISearchView provideSearchView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public final SearchContract.ISearchModel provideSearchModel(HotSearchModel model) {
        return model;
    }

}
