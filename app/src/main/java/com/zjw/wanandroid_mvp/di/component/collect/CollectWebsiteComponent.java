package com.zjw.wanandroid_mvp.di.component.collect;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.di.module.collect.CollectWebsiteModule;
import com.zjw.wanandroid_mvp.ui.main.collect.CollectWebsiteFragment;

import dagger.Component;

@FragmentScope
@Component(modules = CollectWebsiteModule.class, dependencies = AppComponent.class)
public interface CollectWebsiteComponent {

    void inject(CollectWebsiteFragment fragment);
}
