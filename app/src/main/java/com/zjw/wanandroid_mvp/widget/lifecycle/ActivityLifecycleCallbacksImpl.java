package com.zjw.wanandroid_mvp.widget.lifecycle;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import timber.log.Timber;


public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {
    @Override
    public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(@NonNull Activity activity) {
        Timber.d("onActivityStarted: ");
    }

    @Override
    public void onActivityResumed(@NonNull Activity activity) {
        Timber.d("onActivityResumed: ");
    }

    @Override
    public void onActivityPaused(@NonNull Activity activity) {
        Timber.d("onActivityPaused: ");
    }

    @Override
    public void onActivityStopped(@NonNull Activity activity) {
        Timber.d("onActivityStopped: ");
    }

    @Override
    public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
        Timber.d("onActivitySaveInstanceState: ");
    }

    @Override
    public void onActivityDestroyed(@NonNull Activity activity) {
        Timber.d("onActivityDestroyed: ");
    }
}
