package com.zjw.wanandroid_mvp.di.module.main.todo;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.contract.todo.AddTodoContract;
import com.zjw.wanandroid_mvp.contract.todo.TodoListContract;
import com.zjw.wanandroid_mvp.model.main.todo.AddTodoModel;
import com.zjw.wanandroid_mvp.model.main.todo.TodoListModel;

import dagger.Module;
import dagger.Provides;

@Module
public class AddTodoModule {

    private final AddTodoContract.IAddTodoView view;

    public AddTodoModule(AddTodoContract.IAddTodoView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public AddTodoContract.IAddTodoView provideAddTodoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public AddTodoContract.IAddTodoModel provideAddTodoModule(AddTodoModel model) {
        return model;
    }
}
