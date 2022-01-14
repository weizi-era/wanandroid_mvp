package com.zjw.wanandroid_mvp.di.component.main;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.di.module.main.QuestionModule;
import com.zjw.wanandroid_mvp.ui.main.QuestionActivity;

import dagger.Component;

@ActivityScope
@Component(modules = QuestionModule.class, dependencies = AppComponent.class)
public interface QuestionComponent {

    void inject(QuestionActivity activity);
}
