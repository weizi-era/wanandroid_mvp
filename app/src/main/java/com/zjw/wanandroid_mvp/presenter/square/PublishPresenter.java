package com.zjw.wanandroid_mvp.presenter.square;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.base.BasePresenter;
import com.zjw.wanandroid_mvp.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.square.PublishContract;
import com.zjw.wanandroid_mvp.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@ActivityScope
public class PublishPresenter extends BasePresenter<PublishContract.IPublishModel, PublishContract.IPublishView> {

    @Inject
    public PublishPresenter(PublishContract.IPublishModel model, PublishContract.IPublishView rootView) {
        super(model, rootView);
    }

    public void addArticle(String title, String link) {
        mModel.addArticle(title, link).subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Observer<BaseBean<Object>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        mRootView.showLoading("正在发布中...");
                    }

                    @Override
                    public void onNext(@NotNull BaseBean<Object> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            mRootView.addArticle();
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
