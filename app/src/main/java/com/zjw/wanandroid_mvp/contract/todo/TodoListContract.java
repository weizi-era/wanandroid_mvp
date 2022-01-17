package com.zjw.wanandroid_mvp.contract.todo;

import com.jess.arms.mvp.IModel;
import com.zjw.wanandroid_mvp.base.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.bean.TodoListBean;

import java.util.List;

import io.reactivex.Observable;

public interface TodoListContract {

    interface ITodoListView extends IView {
        void showTodoList(BasePageBean<List<TodoListBean>> bean);
        void deleteTodo(int position);
    }

    interface ITodoListModel extends IModel{
        Observable<BaseBean<BasePageBean<List<TodoListBean>>>> getTodoList(int page);
        Observable<BaseBean<Object>> deleteTodo(int id);
    }
}
