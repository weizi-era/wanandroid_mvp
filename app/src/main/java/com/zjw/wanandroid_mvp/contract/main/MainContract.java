package com.zjw.wanandroid_mvp.contract.main;

import com.jess.arms.mvp.IModel;
import com.zjw.wanandroid_mvp.base.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.CoinInfo;

import io.reactivex.Observable;


public interface MainContract {

    interface IMainView extends IView {
        void showUserInfo(CoinInfo bean);
        void showLogoutSuccess();
    }

    interface IMainModel extends IModel {
        Observable<BaseBean<CoinInfo>> loadUserInfo();
        Observable<BaseBean<Object>> logout();
    }
}
