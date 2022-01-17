package com.zjw.wanandroid_mvp.contract.main;

import com.jess.arms.mvp.IModel;
import com.zjw.wanandroid_mvp.base.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.UserBean;

import io.reactivex.Observable;


public interface RegisterContract {

    interface IRegisterView extends IView {
        void registerResult(UserBean bean);
    }

    interface IRegisterModel extends IModel {
        Observable<BaseBean<UserBean>> register(String userName, String passWord, String repassword);
    }
}
