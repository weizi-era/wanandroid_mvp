package com.zjw.wanandroid_mvp.presenter.main;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.base.BasePresenter;
import com.zjw.wanandroid_mvp.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.UserBean;
import com.zjw.wanandroid_mvp.contract.main.RegisterContract;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.utils.HttpUtils;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class RegisterPresenter extends BasePresenter<RegisterContract.IRegisterModel, RegisterContract.IRegisterView> {

    @Inject
    public RegisterPresenter(RegisterContract.IRegisterModel model, RegisterContract.IRegisterView rootView) {
        super(model, rootView);
    }

    public void register(String userName, String passWord, String repassword) {

        mModel.register(userName, passWord, repassword)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Observer<BaseBean<UserBean>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                       mRootView.showLoading("正在注册中...");
                    }

                    @Override
                    public void onNext(@NonNull BaseBean<UserBean> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            mRootView.registerResult(bean.getData());
                        } else {
                            mRootView.showMessage(bean.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mRootView.showMessage(HttpUtils.INSTANCE.getErrorText(e));
                    }

                    @Override
                    public void onComplete() {
                        mRootView.hideLoading();
                    }
                });
    }
}
