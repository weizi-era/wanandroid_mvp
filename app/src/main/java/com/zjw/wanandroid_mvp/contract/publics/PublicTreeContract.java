package com.zjw.wanandroid_mvp.contract.publics;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.TreeBean;

import java.util.List;

import io.reactivex.Observable;

public interface PublicTreeContract {

    interface IPublicTreeView extends IView {
        void showPublicCategory(List<TreeBean> bean);
    }

    interface IPublicTreeModel extends IModel {
        Observable<BaseBean<List<TreeBean>>> getPublicCategory();
    }

}
