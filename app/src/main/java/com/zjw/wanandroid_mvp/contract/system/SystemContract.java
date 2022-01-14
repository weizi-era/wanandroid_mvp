package com.zjw.wanandroid_mvp.contract.system;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.SystemBean;
import com.zjw.wanandroid_mvp.bean.TreeBean;

import java.util.List;

import io.reactivex.Observable;

public interface SystemContract {

    interface ISystemView extends IView {
        void showSystemList(List<SystemBean> bean);
    }

    interface ISystemModel extends IModel {
        Observable<BaseBean<List<SystemBean>>> getSystemList();
    }
}
