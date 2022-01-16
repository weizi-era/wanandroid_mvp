package com.zjw.wanandroid_mvp.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CollectView extends androidx.appcompat.widget.AppCompatImageView {

    private boolean isChecked;

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public CollectView(@NonNull Context context) {
        super(context);
    }

    public CollectView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CollectView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public boolean isChecked() {
        return isChecked;
    }
}
