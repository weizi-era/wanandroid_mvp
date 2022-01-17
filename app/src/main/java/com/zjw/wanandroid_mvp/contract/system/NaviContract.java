package com.zjw.wanandroid_mvp.contract.system;

import com.jess.arms.mvp.IModel;
import com.zjw.wanandroid_mvp.base.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.NaviBean;


import java.util.List;

import io.reactivex.Observable;

public interface NaviContract {

    interface INaviView extends IView {
        void showNaviArticleList(List<NaviBean> beans);
    }

    interface INaviModel extends IModel {
        Observable<BaseBean<List<NaviBean>>> getNaviArticleList();
    }

}
