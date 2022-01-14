package com.zjw.wanandroid_mvp.di.component.main;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.di.module.main.RegisterModule;
import com.zjw.wanandroid_mvp.ui.main.RegisterActivity;

import dagger.Component;

@ActivityScope
@Component(modules = RegisterModule.class, dependencies = AppComponent.class)
public interface RegisterComponent {

    void inject(RegisterActivity activity);
}
