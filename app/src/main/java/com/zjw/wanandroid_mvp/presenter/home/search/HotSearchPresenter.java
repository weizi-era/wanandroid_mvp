package com.zjw.wanandroid_mvp.presenter.home.search;

import com.google.gson.Gson;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.HotSearchBean;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.home.search.SearchContract;
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

@ActivityScope
public class HotSearchPresenter extends BasePresenter<SearchContract.ISearchModel, SearchContract.ISearchView> {

    @Inject
    public HotSearchPresenter(SearchContract.ISearchModel model, SearchContract.ISearchView rootView) {
        super(model, rootView);
    }

    public void getHotSearch() {
        List<HotSearchBean> hotSearchCache = CacheUtil.getHotSearchCache();
        if (hotSearchCache.size() != 0) {
            mRootView.showHotSearch(hotSearchCache);
        }
        mModel.getHotSearch().subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(1, 0))
                .observeOn(AndroidSchedulers.mainThread())
                .compose(RxLifecycleUtils.bindToLifecycle(mRootView))
                .subscribe(new Observer<BaseBean<List<HotSearchBean>>>() {
                    @Override
                    public void onSubscribe(@NotNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NotNull BaseBean<List<HotSearchBean>> bean) {
                        if (bean.getErrorCode() == Constant.SUCCESS) {
                            CacheUtil.setHotSearchCache(new Gson().toJson(bean.getData()));
                            if (hotSearchCache.size() == 0) {
                                mRootView.showHotSearch(bean.getData());
                            }
                        } else {
                            if (hotSearchCache.size() == 0) {
                                mRootView.showHotSearch(hotSearchCache);
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
