package com.zjw.wanandroid_mvp.di.module.main;

import com.jess.arms.di.scope.ActivityScope;
import com.zjw.wanandroid_mvp.contract.main.QuestionContract;
import com.zjw.wanandroid_mvp.model.main.QuestionModel;

import org.jetbrains.annotations.NotNull;

import dagger.Module;
import dagger.Provides;

@Module
public class QuestionModule {

    private final QuestionContract.IQuestionView view;

    public QuestionModule(@NotNull QuestionContract.IQuestionView view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    public final QuestionContract.IQuestionView provideQuestionView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    public final QuestionContract.IQuestionModel provideLoginModel(QuestionModel model) {
        return model;
    }

}
