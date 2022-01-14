package com.zjw.wanandroid_mvp.model.main;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.UserBean;
import com.zjw.wanandroid_mvp.contract.main.RegisterContract;
import com.zjw.wanandroid_mvp.model.api.ApiServer;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class RegisterModel extends BaseModel implements RegisterContract.IRegisterModel {


    @Inject
    public RegisterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<UserBean>> register(String userName, String passWord, String repassword) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).register(userName, passWord, repassword);
    }
}
