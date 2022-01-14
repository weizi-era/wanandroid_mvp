package com.zjw.wanandroid_mvp.di.component.main;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.di.module.main.IntegralModule;
import com.zjw.wanandroid_mvp.ui.main.IntegralActivity;

import dagger.Component;

@ActivityScope
@Component(modules = IntegralModule.class, dependencies = AppComponent.class)
public interface IntegralComponent {

    void inject(IntegralActivity activity);
}
