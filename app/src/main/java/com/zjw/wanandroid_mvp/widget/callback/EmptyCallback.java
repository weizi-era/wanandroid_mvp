package com.zjw.wanandroid_mvp.widget.callback;

import android.content.Context;

import com.kingja.loadsir.callback.Callback;
import com.zjw.wanandroid_mvp.R;

public class EmptyCallback extends Callback {
    @Override
    protected int onCreateView() {
        return R.layout.layout_empty;
    }

}
