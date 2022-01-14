package com.zjw.wanandroid_mvp.di.component.home.search;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.di.module.home.search.SearchModule;
import com.zjw.wanandroid_mvp.ui.home.SearchActivity;

import dagger.Component;

@ActivityScope
@Component(modules = SearchModule.class, dependencies = AppComponent.class)
public interface SearchComponent {

    void inject(SearchActivity activity);
}
