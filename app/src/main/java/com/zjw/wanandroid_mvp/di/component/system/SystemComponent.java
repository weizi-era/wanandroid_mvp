package com.zjw.wanandroid_mvp.di.component.system;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.di.module.square.PublishModule;
import com.zjw.wanandroid_mvp.di.module.system.SystemModule;
import com.zjw.wanandroid_mvp.ui.system.NaviListFragment;
import com.zjw.wanandroid_mvp.ui.system.SystemFragment;
import com.zjw.wanandroid_mvp.ui.system.SystemListFragment;

import dagger.Component;

@FragmentScope
@Component(modules = SystemModule.class, dependencies = AppComponent.class)
public interface SystemComponent {

    void inject(SystemListFragment fragment);
}
