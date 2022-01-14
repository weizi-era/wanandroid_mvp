package com.zjw.wanandroid_mvp.utils;

import android.content.Context;
import android.content.Intent;

import com.zjw.wanandroid_mvp.ui.web.WebViewActivity;


public class JumpWebUtils {

    public static void startWebActivity(Context context, String title, String url) {
        Intent intent = new Intent();
        intent.setClass(context, WebViewActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void startWebActivity(Context context, int id, String title, String url) {
        Intent intent = new Intent();
        intent.setClass(context, WebViewActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("title", title);
        intent.putExtra("url", url);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
