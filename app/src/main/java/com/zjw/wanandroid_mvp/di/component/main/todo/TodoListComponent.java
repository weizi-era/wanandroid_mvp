package com.zjw.wanandroid_mvp.di.component.main.todo;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.di.module.main.todo.TodoListModule;
import com.zjw.wanandroid_mvp.ui.main.todo.TodoActivity;

import dagger.Component;

@ActivityScope
@Component(modules = TodoListModule.class, dependencies = AppComponent.class)
public interface TodoListComponent {

    void inject(TodoActivity activity);
}
