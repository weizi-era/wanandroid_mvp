package com.zjw.wanandroid_mvp.model.main.todo;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.TodoBean;
import com.zjw.wanandroid_mvp.contract.todo.AddTodoContract;
import com.zjw.wanandroid_mvp.model.api.ApiServer;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class AddTodoModel extends BaseModel implements AddTodoContract.IAddTodoModel {

    @Inject
    public AddTodoModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<TodoBean>> addTodo(String title, String content, String date, int type, int priority) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).addTodo(title, content, date, type, priority);
    }

    @Override
    public Observable<BaseBean<TodoBean>> updateTodo(int id, String title, String content, String date, int type, int priority) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).updateTodo(id, title, content, date, type, priority);
    }
}
