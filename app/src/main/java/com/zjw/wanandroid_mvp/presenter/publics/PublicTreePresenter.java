package com.zjw.wanandroid_mvp.presenter.publics;

import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.base.BasePresenter;
import com.zjw.wanandroid_mvp.utils.RxLifecycleUtils;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.TreeBean;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.publics.PublicTreeContract;
import com.zjw.wanandroid_mvp.utils.CacheUtil;
import com.zjw.wanandroid_mvp.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@FragmentScope
public class PublicTreePresenter extends BasePresenter<PublicTreeContract.IPublicTreeModel, PublicTreeContract.IPublicTreeView> {

    @Inject
    public PublicTreePresenter(PublicTreeContract.IPublicTreeModel model, PublicTreeContract.IPublicTreeView rootView) {
        super(model, rootView);
    }

    public void getPublicCategory() {
        List<TreeBean> publicTagCache = CacheUtil.getPublicTagCache();
        if (publicTagCache.size() != 0) {
            mRootView.showPublicCategory(publicTagCache);
        }
        mModel.getPublicCategory()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseBean<List<TreeBean>>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull BaseBean<List<TreeBean>> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            CacheUtil.setPublicTagCache(new Gson().toJson(bean.getData()));
                            if (publicTagCache.size() == 0) {
                                mRootView.showPublicCategory(bean.getData());
                            }
                        } else {
                            if (publicTagCache.size() == 0) {
                                mRootView.showPublicCategory(publicTagCache);
                            }
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
}
