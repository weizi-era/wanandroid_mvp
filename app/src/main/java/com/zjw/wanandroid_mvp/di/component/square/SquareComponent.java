package com.zjw.wanandroid_mvp.di.component.square;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.FragmentScope;
import com.zjw.wanandroid_mvp.di.module.square.SquareModule;
import com.zjw.wanandroid_mvp.ui.square.SquareFragment;

import dagger.Component;

@FragmentScope
@Component(modules = SquareModule.class, dependencies = AppComponent.class)
public interface SquareComponent {

    void inject(SquareFragment fragment);
}
