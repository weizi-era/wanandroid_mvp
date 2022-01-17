package com.zjw.wanandroid_mvp.presenter.system;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.base.BasePresenter;
import com.zjw.wanandroid_mvp.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.system.SystemArticleContract;
import com.zjw.wanandroid_mvp.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class SystemArticlePresenter extends BasePresenter<SystemArticleContract.ISystemArticleModel, SystemArticleContract.ISystemArticleView> {

    @Inject
    public SystemArticlePresenter(SystemArticleContract.ISystemArticleModel model, SystemArticleContract.ISystemArticleView rootView) {
        super(model, rootView);
    }

    public void getArticleList(int page, int cid) {
        mModel.getArticleList(page, cid).subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Observer<BaseBean<BasePageBean<List<ArticleBean>>>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull BaseBean<BasePageBean<List<ArticleBean>>> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            mRootView.showArticleList(bean.getData());
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

                    }
                });
    }

    public void collect(int articleId, int position) {
        mModel.collect(articleId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Observer<BaseBean<Object>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull BaseBean<Object> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            mRootView.collect(true, position);
                        } else {
                            mRootView.collect(false, position);
                            mRootView.showMessage(bean.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        mRootView.collect(false, position);
                        mRootView.showMessage(HttpUtils.INSTANCE.getErrorText(e));
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void unCollect(int articleId, int position) {
        mModel.unCollect(articleId)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Observer<BaseBean<Object>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull BaseBean<Object> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            mRootView.collect(false, position);
                        } else {
                            mRootView.collect(true, position);
                            mRootView.showMessage(bean.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        mRootView.collect(true, position);
                        mRootView.showMessage(HttpUtils.INSTANCE.getErrorText(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
