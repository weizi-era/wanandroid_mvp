package com.zjw.wanandroid_mvp.di.module.main;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.contract.main.RegisterContract;
import com.zjw.wanandroid_mvp.model.main.RegisterModel;

import dagger.Module;
import dagger.Provides;

@Module
public class RegisterModule {

    private final RegisterContract.IRegisterView view;

    public RegisterModule(RegisterContract.IRegisterView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public final RegisterContract.IRegisterView provideRegisterView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public final RegisterContract.IRegisterModel provideRegisterModel(RegisterModel model) {
        return model;
    }
}
