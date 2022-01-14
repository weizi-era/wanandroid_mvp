package com.zjw.wanandroid_mvp.di.component.project;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.di.module.project.ProjectListModule;
import com.zjw.wanandroid_mvp.ui.project.ProjectListFragment;

import dagger.Component;

@FragmentScope
@Component(modules = ProjectListModule.class, dependencies = AppComponent.class)
public interface ProjectListComponent {

    void inject(ProjectListFragment fragment);
}
