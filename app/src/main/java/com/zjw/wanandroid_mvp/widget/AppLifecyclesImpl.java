package com.zjw.wanandroid_mvp.widget;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.Utils;
import com.jess.arms.base.delegate.AppLifecycles;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadSir;
import com.tencent.mmkv.MMKV;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.ErrorCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.jetbrains.annotations.NotNull;

public class AppLifecyclesImpl implements AppLifecycles {
    @Override
    public void attachBaseContext(@NonNull @NotNull Context base) {

    }

    @Override
    public void onCreate(@NonNull @NotNull Application application) {

        //工具类 初始化
        Utils.init(application);

        // mmkv 初始化
        MMKV.initialize(application.getFilesDir().getAbsolutePath() + "/mmkv");

        //界面加载管理 初始化
        LoadSir.beginBuilder()
                .addCallback(new LoadingCallback())//加载
                .addCallback(new ErrorCallback())//错误
                .addCallback(new EmptyCallback())//空
                .setDefaultCallback(SuccessCallback.class)//设置默认加载状态页
                .commit();

    }

    @Override
    public void onTerminate(@NonNull @NotNull Application application) {

    }
}
