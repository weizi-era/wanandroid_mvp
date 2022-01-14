package com.zjw.wanandroid_mvp.contract.main;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.UserBean;

import io.reactivex.Observable;


public interface LoginContract {

    interface ILoginView extends IView {
        void loginSuccess(UserBean bean);
    }

    interface ILoginModel extends IModel {
        Observable<BaseBean<UserBean>> login(String userName, String passWord);
    }

}
