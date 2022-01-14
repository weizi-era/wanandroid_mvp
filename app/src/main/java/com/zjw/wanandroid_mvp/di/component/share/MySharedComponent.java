package com.zjw.wanandroid_mvp.di.component.share;


import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.di.module.share.MySharedModule;
import com.zjw.wanandroid_mvp.ui.main.share.MySharedActivity;

import dagger.Component;


@ActivityScope
@Component(modules = MySharedModule.class, dependencies = AppComponent.class)
public interface MySharedComponent {

    void inject(MySharedActivity activity);
}
