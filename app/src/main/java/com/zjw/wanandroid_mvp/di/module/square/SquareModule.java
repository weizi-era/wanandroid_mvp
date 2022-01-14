package com.zjw.wanandroid_mvp.di.module.square;

import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.contract.collect.CollectArticleContract;
import com.zjw.wanandroid_mvp.contract.square.SquareContract;
import com.zjw.wanandroid_mvp.model.collect.CollectArticleModel;
import com.zjw.wanandroid_mvp.model.square.SquareModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class SquareModule {

    private final SquareContract.ISquareView view;

    public SquareModule(@NotNull SquareContract.ISquareView view) {
        this.view = view;
    }

    @FragmentScope
    @Provides
    public final SquareContract.ISquareView provideSquareView() {
        return this.view;
    }

    @FragmentScope
    @Provides
    public final SquareContract.ISquareModel provideSquareModel(SquareModel model) {
        return model;
    }

}
