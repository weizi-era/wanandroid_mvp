package com.zjw.wanandroid_mvp.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;


public class ScaleDownShowBehavior extends FloatingActionButton.Behavior {

    public ScaleDownShowBehavior(Context context, AttributeSet attr) {
        super(context, attr);
    }

    @Override
    public void onNestedScroll(@NonNull @NotNull CoordinatorLayout coordinatorLayout, @NonNull @NotNull FloatingActionButton child, @NonNull @NotNull View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int type, @NonNull @NotNull int[] consumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, type, consumed);

        if (dyConsumed > 0 && child.getVisibility() == View.VISIBLE) {
            child.setVisibility(View.INVISIBLE);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            child.show();
        }
    }

    public boolean onStartNestedScroll(@NotNull CoordinatorLayout coordinatorLayout,
                                       @NotNull FloatingActionButton child, View directTargetChild, View target,
                                       int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL || super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, axes, type);
    }
}
