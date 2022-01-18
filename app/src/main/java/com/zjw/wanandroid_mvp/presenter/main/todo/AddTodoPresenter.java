package com.zjw.wanandroid_mvp.presenter.main.todo;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.base.BasePresenter;
import com.zjw.wanandroid_mvp.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.TodoBean;
import com.zjw.wanandroid_mvp.contract.todo.AddTodoContract;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;


import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class AddTodoPresenter extends BasePresenter<AddTodoContract.IAddTodoModel, AddTodoContract.IAddTodoView> {

    @Inject
    public AddTodoPresenter(AddTodoContract.IAddTodoModel model, AddTodoContract.IAddTodoView rootView) {
        super(model, rootView);
    }

    public void addTodo(String title, String content, String date, int type, int priority) {
        mModel.addTodo(title, content, date, type, priority).subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Observer<BaseBean<TodoBean>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        mRootView.showLoading("正在提交中...");
                    }

                    @Override
                    public void onNext(@NotNull BaseBean<TodoBean> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            mRootView.addTodo();
                        } else {
                            mRootView.showMessage(bean.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        mRootView.showMessage(HttpUtils.INSTANCE.getErrorText(e));
                    }

                    @Override
                    public void onComplete() {
                        mRootView.hideLoading();
                    }
                });
    }

    public void updateTodo(int id, String title, String content, String date, int type, int priority) {
        mModel.updateTodo(id, title, content, date, type, priority).subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Observer<BaseBean<TodoBean>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        mRootView.showLoading("正在更新中...");
                    }

                    @Override
                    public void onNext(@NotNull BaseBean<TodoBean> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            mRootView.updateTodo();
                        } else {
                            mRootView.showMessage(bean.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        mRootView.showMessage(HttpUtils.INSTANCE.getErrorText(e));
                    }

                    @Override
                    public void onComplete() {
                        mRootView.hideLoading();
                    }
                });
    }

}
