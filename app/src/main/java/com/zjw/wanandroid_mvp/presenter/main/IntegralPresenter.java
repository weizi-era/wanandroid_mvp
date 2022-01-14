package com.zjw.wanandroid_mvp.presenter.main;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.bean.ScoreListBean;
import com.zjw.wanandroid_mvp.contract.main.IntegralContract;
import com.zjw.wanandroid_mvp.model.constant.Constant;
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
public class IntegralPresenter extends BasePresenter<IntegralContract.IIntegralModel, IntegralContract.IIntegralView> {

    @Inject
    public IntegralPresenter(IntegralContract.IIntegralModel model, IntegralContract.IIntegralView rootView) {
        super(model, rootView);
    }

    public void getScoreList(int page) {

        mModel.getScoreList(page).subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, ActivityEvent.DESTROY))
                .subscribe(new Observer<BaseBean<BasePageBean<List<ScoreListBean>>>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {
                        mRootView.showLoading();
                    }

                    @Override
                    public void onNext(@NotNull BaseBean<BasePageBean<List<ScoreListBean>>> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            mRootView.showScoreList(bean.getData());
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
