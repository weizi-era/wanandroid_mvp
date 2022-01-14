package com.zjw.wanandroid_mvp.utils;

import android.content.Context;
import android.view.Gravity;

import com.blankj.utilcode.util.ToastUtils;
import com.zjw.wanandroid_mvp.R;

public class ToastUtil {

    public static void show(Context context, String msg) {
        ToastUtils.getDefaultMaker()
                .setBgResource(R.drawable.toast_bg)
                .setTextColor(context.getResources().getColor(R.color.white))
                .setGravity(Gravity.CENTER, 0, 0)
                .show(msg);
    }
}
