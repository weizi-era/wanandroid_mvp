package com.zjw.wanandroid_mvp.di.module.home.search;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.contract.home.search.SearchContract;
import com.zjw.wanandroid_mvp.contract.home.search.SearchResultContract;
import com.zjw.wanandroid_mvp.model.home.search.HotSearchModel;
import com.zjw.wanandroid_mvp.model.home.search.SearchResultModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchResultModule {

    private final SearchResultContract.ISearchResultView view;

    public SearchResultModule(@NotNull SearchResultContract.ISearchResultView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public final SearchResultContract.ISearchResultView provideSearchResultView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public final SearchResultContract.ISearchResultModel provideSearchResultModel(SearchResultModel model) {
        return model;
    }

}
