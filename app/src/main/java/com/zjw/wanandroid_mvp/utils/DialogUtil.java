package com.zjw.wanandroid_mvp.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zjw.wanandroid_mvp.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class DialogUtil {

    private static ProgressDialog dialog;

    public static void showLoading(Context context) {
        dialog = new ProgressDialog(context);
        if (dialog.isShowing()) {
            return;
        }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage("正在登录中...");
            ClipDrawable d = new ClipDrawable(new ColorDrawable(Utils.getColor(context)), Gravity.START, ClipDrawable.HORIZONTAL);
            dialog.setProgressDrawable(d);
            dialog.show();
        }
    }

    public static void dismissLoading() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog = null;
    }

    public static void showConfirmDialog(Activity activity, String message, ConfirmListener listener) {
        if (!activity.isFinishing()) {
           new MaterialDialog(activity, MaterialDialog.getDEFAULT_BEHAVIOR()).show(materialDialog -> {
               materialDialog.title(R.string.title, null).message(null, message, null)
                       .positiveButton(R.string.confirm, null, dialog -> {
                           listener.onConfirm();
                           return null;
                       })
                       .negativeButton(R.string.cancel, null, dialog -> null)
                       .show();
               return null;
           });
        }
    }

    public static void showDialog(Activity activity, String message) {
        if (!activity.isFinishing()) {
            new MaterialDialog(activity, MaterialDialog.getDEFAULT_BEHAVIOR()).show(materialDialog -> {
                materialDialog.title(R.string.title, null).message(null, message, null)
                        .positiveButton(R.string.confirm, null, dialog -> null)
                        .show();
                return null;
            });
        }
    }

    public static void showDialog(Activity activity, String message, String title) {
        if (!activity.isFinishing()) {
            new MaterialDialog(activity, MaterialDialog.getDEFAULT_BEHAVIOR()).show(materialDialog -> {
                materialDialog.title(null, title).message(null, message, null)
                        .positiveButton(R.string.confirm, null, dialog -> null)
                        .show();
                return null;
            });
        }
    }

    public interface ConfirmListener {
        void onConfirm();
    }
}
