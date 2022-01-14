package com.zjw.wanandroid_mvp.presenter.home;


import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BannerBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.home.HomeContract;
import com.zjw.wanandroid_mvp.utils.HttpUtils;

import org.jetbrains.annotations.NotNull;


import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.handler.RetryWithDelay;

@FragmentScope
public class HomePresenter extends BasePresenter<HomeContract.IHomeModel, HomeContract.IHomeView> {

    @Inject
    public HomePresenter(HomeContract.IHomeModel model, HomeContract.IHomeView rootView) {
        super(model, rootView);
    }

    public void loadBanner() {

        mModel.loadBanner()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, FragmentEvent.DESTROY))
                .subscribe(new Observer<BaseBean<List<BannerBean>>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                    }

                    @Override
                    public void onNext(@NonNull BaseBean<List<BannerBean>> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            mRootView.showBanner(bean.getData());
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

                    }
                });
    }

    public void getHomeArticle(int page) {

        mModel.getHomeArticle(page)
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, FragmentEvent.DESTROY))
                .subscribe(new Observer<BaseBean<BasePageBean<List<ArticleBean>>>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull BaseBean<BasePageBean<List<ArticleBean>>> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            BasePageBean<List<ArticleBean>> data = bean.getData();
                            if (page == 0) {
                                mModel.getTopArticle()
                                        .subscribeOn(Schedulers.io())
                                        .retryWhen(new RetryWithDelay(1, 0))
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .compose(RxLifecycleUtils.bindUntilEvent(mRootView, FragmentEvent.DESTROY))
                                        .subscribe(new Observer<BaseBean<List<ArticleBean>>>() {
                                            @Override
                                            public void onSubscribe(@NotNull Disposable d) {

                                            }

                                            @Override
                                            public void onNext(@NotNull BaseBean<List<ArticleBean>> bean1) {
                                                if (bean1.getErrorCode() == Constant.SUCCESS) {
                                                    data.getDatas().addAll(0, bean1.getData());
                                                }
                                                mRootView.showHomeArticle(data);
                                            }

                                            @Override
                                            public void onError(@NotNull Throwable e) {
                                                mRootView.showMessage(HttpUtils.INSTANCE.getErrorText(e));
                                            }

                                            @Override
                                            public void onComplete() {

                                            }
                                        });
                            } else {
                                mRootView.showHomeArticle(data);
                            }
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
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, FragmentEvent.DESTROY))
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
                .compose(RxLifecycleUtils.bindUntilEvent(mRootView, FragmentEvent.DESTROY))
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
