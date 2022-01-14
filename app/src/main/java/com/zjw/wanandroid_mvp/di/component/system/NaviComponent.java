package com.zjw.wanandroid_mvp.di.component.system;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.di.module.square.PublishModule;
import com.zjw.wanandroid_mvp.di.module.system.NaviModule;
import com.zjw.wanandroid_mvp.ui.square.PublishActivity;
import com.zjw.wanandroid_mvp.ui.system.NaviListFragment;

import dagger.Component;

@FragmentScope
@Component(modules = NaviModule.class, dependencies = AppComponent.class)
public interface NaviComponent {

    void inject(NaviListFragment fragment);
}
