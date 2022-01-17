package com.zjw.wanandroid_mvp.di.module.main.todo;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.contract.todo.TodoListContract;
import com.zjw.wanandroid_mvp.model.main.todo.TodoListModel;

import dagger.Module;
import dagger.Provides;

@Module
public class TodoListModule {

    private final TodoListContract.ITodoListView view;

    public TodoListModule(TodoListContract.ITodoListView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public TodoListContract.ITodoListView provideTodoListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public TodoListContract.ITodoListModel provideTodoListModule(TodoListModel model) {
        return model;
    }
}
