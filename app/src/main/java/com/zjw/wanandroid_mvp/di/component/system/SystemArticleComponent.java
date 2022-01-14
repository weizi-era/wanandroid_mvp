package com.zjw.wanandroid_mvp.di.component.system;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.di.module.square.PublishModule;
import com.zjw.wanandroid_mvp.di.module.system.SystemArticleModule;
import com.zjw.wanandroid_mvp.ui.square.PublishActivity;
import com.zjw.wanandroid_mvp.ui.system.SystemArticleActivity;

import dagger.Component;

@ActivityScope
@Component(modules = SystemArticleModule.class, dependencies = AppComponent.class)
public interface SystemArticleComponent {

    void inject(SystemArticleActivity activity);
}
