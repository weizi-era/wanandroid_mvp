package com.zjw.wanandroid_mvp.contract.todo;

import com.jess.arms.mvp.IModel;
import com.zjw.wanandroid_mvp.base.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.bean.TodoBean;

import java.util.List;

import io.reactivex.Observable;

public interface TodoListContract {

    interface ITodoListView extends IView {
        void showTodoList(BasePageBean<List<TodoBean>> bean);
        void deleteTodo(int position);
        void completeTodo(int position, TodoBean bean);
    }

    interface ITodoListModel extends IModel{
        Observable<BaseBean<BasePageBean<List<TodoBean>>>> getTodoList(int page);
        Observable<BaseBean<Object>> deleteTodo(int id);
        Observable<BaseBean<TodoBean>> completeTodo(int id, int status);
    }
}
