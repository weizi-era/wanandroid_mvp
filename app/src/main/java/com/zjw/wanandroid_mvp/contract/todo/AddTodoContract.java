package com.zjw.wanandroid_mvp.contract.todo;

import com.jess.arms.mvp.IModel;
import com.zjw.wanandroid_mvp.base.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.TodoListBean;


import io.reactivex.Observable;

public interface AddTodoContract {

    interface IAddTodoView extends IView {
        void addTodo();
    }

    interface IAddTodoModel extends IModel {
        Observable<BaseBean<TodoListBean>> addTodo(String title, String content, String date, int type, int priority);
    }
}
