package com.zjw.wanandroid_mvp.di.component.collect;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.di.module.collect.CollectArticleModule;
import com.zjw.wanandroid_mvp.ui.main.collect.CollectArticleFragment;

import dagger.Component;

@FragmentScope
@Component(modules = CollectArticleModule.class, dependencies = AppComponent.class)
public interface CollectArticleComponent {

    void inject(CollectArticleFragment fragment);
}
