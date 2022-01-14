package com.zjw.wanandroid_mvp.di.component.main;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.di.module.main.RankListModule;
import com.zjw.wanandroid_mvp.ui.main.RankActivity;

import dagger.Component;

@ActivityScope
@Component(modules = RankListModule.class, dependencies = AppComponent.class)
public interface RankListComponent {
    void inject(RankActivity activity);
}
