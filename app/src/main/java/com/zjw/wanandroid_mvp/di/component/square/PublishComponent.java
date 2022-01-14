package com.zjw.wanandroid_mvp.di.component.square;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.di.module.square.PublishModule;
import com.zjw.wanandroid_mvp.ui.square.PublishActivity;

import dagger.Component;

@ActivityScope
@Component(modules = PublishModule.class, dependencies = AppComponent.class)
public interface PublishComponent {

    void inject(PublishActivity activity);
}
