package com.zjw.wanandroid_mvp.contract.project;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.TreeBean;

import java.util.List;

import io.reactivex.Observable;

public interface ProjectTreeContract {

    interface IProjectTreeView extends IView {
        void showProjectCategory(List<TreeBean> bean);
    }

    interface IProjectTreeModel extends IModel {
        Observable<BaseBean<List<TreeBean>>> getProjectCategory();
    }

}
