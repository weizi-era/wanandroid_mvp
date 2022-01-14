package com.zjw.wanandroid_mvp.di.component.home;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.di.module.home.HomeModule;
import com.zjw.wanandroid_mvp.ui.home.HomeFragment;

import dagger.Component;

@FragmentScope
@Component(modules = HomeModule.class, dependencies = AppComponent.class)
public interface HomeComponent {

    void inject(HomeFragment fragment);
}
