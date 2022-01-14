package com.zjw.wanandroid_mvp.di.component.project;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.di.module.project.ProjectTreeModule;
import com.zjw.wanandroid_mvp.ui.project.ProjectFragment;

import dagger.Component;

@FragmentScope
@Component(modules = ProjectTreeModule.class, dependencies = AppComponent.class)
public interface ProjectTreeComponent {

    void inject(ProjectFragment fragment);
}
