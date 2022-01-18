package com.zjw.wanandroid_mvp.model.main.todo;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.bean.TodoBean;
import com.zjw.wanandroid_mvp.contract.todo.TodoListContract;
import com.zjw.wanandroid_mvp.model.api.ApiServer;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class TodoListModel extends BaseModel implements TodoListContract.ITodoListModel {

    @Inject
    public TodoListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<BasePageBean<List<TodoBean>>>> getTodoList(int page) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getTodoList(page);
    }

    @Override
    public Observable<BaseBean<Object>> deleteTodo(int id) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).deleteTodo(id);
    }

    @Override
    public Observable<BaseBean<TodoBean>> completeTodo(int id, int status) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).completeTodo(id, status);
    }
}
