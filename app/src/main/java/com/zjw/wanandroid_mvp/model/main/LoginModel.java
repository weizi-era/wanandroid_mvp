package com.zjw.wanandroid_mvp.model.main;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.UserBean;
import com.zjw.wanandroid_mvp.contract.main.LoginContract;
import com.zjw.wanandroid_mvp.model.api.ApiServer;

import javax.inject.Inject;

import io.reactivex.Observable;


@ActivityScope
public class LoginModel extends BaseModel implements LoginContract.ILoginModel {

    @Inject
    public LoginModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<UserBean>> login(String userName, String passWord) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).login(userName, passWord);
    }

}
