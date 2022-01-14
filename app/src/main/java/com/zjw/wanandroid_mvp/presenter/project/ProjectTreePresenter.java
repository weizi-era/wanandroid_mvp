package com.zjw.wanandroid_mvp.presenter.project;


import com.google.gson.Gson;
import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.mvp.BasePresenter;
import com.jess.arms.utils.RxLifecycleUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.TreeBean;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.project.ProjectTreeContract;
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
public class ProjectTreePresenter extends BasePresenter<ProjectTreeContract.IProjectTreeModel, ProjectTreeContract.IProjectTreeView> {

    @Inject
    public ProjectTreePresenter(ProjectTreeContract.IProjectTreeModel model, ProjectTreeContract.IProjectTreeView rootView) {
        super(model, rootView);
    }

    public void getProjectCategory() {
        List<TreeBean> projectTagCache = CacheUtil.getProjectTagCache();
        if (projectTagCache.size() != 0) {
            mRootView.showProjectCategory(projectTagCache);
        }
        mModel.getProjectCategory().subscribeOn(Schedulers.io())
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
                            CacheUtil.setProjectTagCache(new Gson().toJson(bean.getData()));
                            if (projectTagCache.size() == 0) {
                                mRootView.showProjectCategory(bean.getData());
                            }
                        } else {
                            if (projectTagCache.size() == 0) {
                                mRootView.showProjectCategory(projectTagCache);
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
