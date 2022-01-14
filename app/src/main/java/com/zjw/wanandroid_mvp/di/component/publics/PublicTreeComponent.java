package com.zjw.wanandroid_mvp.di.component.publics;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.di.module.publics.PublicTreeModule;
import com.zjw.wanandroid_mvp.ui.publics.PublicFragment;

import dagger.Component;

@FragmentScope
@Component(modules = PublicTreeModule.class, dependencies = AppComponent.class)
public interface PublicTreeComponent {

    void inject(PublicFragment fragment);
}
