package com.zjw.wanandroid_mvp.di.component.publics;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.di.module.publics.PublicListModule;
import com.zjw.wanandroid_mvp.ui.publics.PublicListFragment;

import dagger.Component;

@FragmentScope
@Component(modules = PublicListModule.class, dependencies = AppComponent.class)
public interface PublicListComponent {

    void inject(PublicListFragment fragment);
}
