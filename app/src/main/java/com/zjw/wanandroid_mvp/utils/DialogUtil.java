package com.zjw.wanandroid_mvp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.afollestad.materialdialogs.MaterialDialog;
import com.zjw.wanandroid_mvp.R;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class DialogUtil {

    private static ProgressDialog dialog;

    public static void showLoading(Context context, String info) {
        dialog = new ProgressDialog(context);
        if (dialog.isShowing()) {
            return;
        }
        if (dialog != null) {
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setMessage(info);
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

    public static void showBottomDialog(Context context) {
        //1、使用Dialog、设置style
        final Dialog dialog = new Dialog(context, R.style.DialogTheme);
        //2、设置布局
        View view = View.inflate(context, R.layout.bottom_dialog_layout,null);
        dialog.setContentView(view);

        Window window = dialog.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        //设置弹出动画
        window.setWindowAnimations(R.style.main_menu_animStyle);
        //设置对话框大小
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.show();

        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

//        dialog.findViewById(R.id.tv_take_pic).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });

    }

    public interface ConfirmListener {
        void onConfirm();
    }
}
