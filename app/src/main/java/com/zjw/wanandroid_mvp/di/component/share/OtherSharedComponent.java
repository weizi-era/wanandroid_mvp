package com.zjw.wanandroid_mvp.di.component.share;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.di.module.share.OtherSharedModule;
import com.zjw.wanandroid_mvp.ui.main.share.OtherSharedActivity;

import dagger.Component;

@ActivityScope
@Component(modules = OtherSharedModule.class, dependencies = AppComponent.class)
public interface OtherSharedComponent {

    void inject(OtherSharedActivity activity);
}
