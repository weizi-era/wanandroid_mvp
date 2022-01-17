package com.zjw.wanandroid_mvp.presenter.share;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.base.BasePresenter;
import com.zjw.wanandroid_mvp.utils.RxLifecycleUtils;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.SharedBean;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.share.OtherShareContract;
import com.zjw.wanandroid_mvp.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class OtherSharePresenter extends BasePresenter<OtherShareContract.IOtherShareModel, OtherShareContract.IOtherShareView> {

    @Inject
    public OtherSharePresenter(@NotNull OtherShareContract.IOtherShareModel model, @NotNull OtherShareContract.IOtherShareView rootView) {
        super(model, rootView);
    }

    public void getOtherShareArticle(int id, int page) {

        mModel.getArticleList(page, id).subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseBean<SharedBean>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull BaseBean<SharedBean> bean) {
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

        mModel.collect(articleId).subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseBean<Object>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull BaseBean<Object> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            mRootView.collect(true, position);
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

    public void unCollect(int articleId, int position) {

        mModel.unCollect(articleId).subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseBean<Object>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull BaseBean<Object> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            mRootView.unCollect(true, position);
                        } else {
                            mRootView.unCollect(false, position);
                            mRootView.showMessage(bean.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(@NotNull Throwable e) {
                        mRootView.unCollect(false, position);
                        mRootView.showMessage(HttpUtils.INSTANCE.getErrorText(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
