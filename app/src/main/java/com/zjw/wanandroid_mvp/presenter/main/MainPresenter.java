package com.zjw.wanandroid_mvp.presenter.main;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.base.BasePresenter;
import com.zjw.wanandroid_mvp.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.CoinInfo;
import com.zjw.wanandroid_mvp.contract.main.MainContract;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;


@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.IMainModel, MainContract.IMainView> {


    @Inject
    public MainPresenter(MainContract.IMainModel model, MainContract.IMainView rootView) {
        super(model, rootView);
    }


    public void logout() {

        mModel.logout().subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Observer<BaseBean<Object>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull BaseBean<Object> data) {
                        if (data.getErrorCode() == Constant.SUCCESS) {
                            mRootView.showLogoutSuccess();
                        } else {
                            mRootView.showMessage(data.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mRootView.showMessage(HttpUtils.INSTANCE.getErrorText(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getUserInfo() {

        mModel.loadUserInfo().subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Observer<BaseBean<CoinInfo>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull BaseBean<CoinInfo> data) {
                        if (data.getErrorCode() == Constant.SUCCESS) {
                            mRootView.showUserInfo(data.getData());
                        } else {
                            mRootView.showMessage(data.getErrorMsg());
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mRootView.showMessage(HttpUtils.INSTANCE.getErrorText(e));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
