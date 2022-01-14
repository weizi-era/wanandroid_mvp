package com.zjw.wanandroid_mvp.di.module.main;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.contract.main.LoginContract;
import com.zjw.wanandroid_mvp.model.main.LoginModel;

import org.jetbrains.annotations.NotNull;


import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    private final LoginContract.ILoginView view;

    public LoginModule(@NotNull LoginContract.ILoginView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public final LoginContract.ILoginView provideLoginView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public final LoginContract.ILoginModel provideLoginModel(LoginModel model) {
        return model;
    }

}
