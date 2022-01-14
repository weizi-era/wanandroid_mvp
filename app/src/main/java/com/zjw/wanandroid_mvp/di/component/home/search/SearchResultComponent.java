package com.zjw.wanandroid_mvp.di.component.home.search;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.di.module.home.search.SearchResultModule;
import com.zjw.wanandroid_mvp.ui.home.SearchResultActivity;

import dagger.Component;

@ActivityScope
@Component(modules = SearchResultModule.class, dependencies = AppComponent.class)
public interface SearchResultComponent {

    void inject(SearchResultActivity activity);
}
