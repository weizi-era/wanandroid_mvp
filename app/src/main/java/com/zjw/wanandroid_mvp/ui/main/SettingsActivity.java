package com.zjw.wanandroid_mvp.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.color.DialogColorChooserExtKt;
import com.blankj.utilcode.util.ToastUtils;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.pgyer.pgyersdk.PgyerSDKManager;
import com.pgyer.pgyersdk.callback.CheckoutCallBack;
import com.pgyer.pgyersdk.model.CheckSoftModel;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.service.DownloadService;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.event.SettingEvent;
import com.zjw.wanandroid_mvp.utils.CacheDataManager;
import com.zjw.wanandroid_mvp.utils.CacheUtil;
import com.zjw.wanandroid_mvp.utils.ColorUtil;
import com.zjw.wanandroid_mvp.utils.DialogUtil;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;

import butterknife.BindView;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

public class SettingsActivity extends BaseActivity<IPresenter> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.clear_cache)
    LinearLayout clear_cache;
    @BindView(R.id.cache_size)
    TextView cache_size;
    @BindView(R.id.ll_web)
    LinearLayout ll_web;
    @BindView(R.id.ll_sourceCode)
    LinearLayout ll_sourceCode;
    @BindView(R.id.ll_download)
    LinearLayout ll_download;
    @BindView(R.id.cb_top)
    CheckBox cb_top;
    @BindView(R.id.ll_version)
    LinearLayout ll_version;
    @BindView(R.id.versionCode)
    TextView versionCode;
    @BindView(R.id.ll_theme_color)
    RelativeLayout ll_theme_color;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_settings;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cache_size.setText(CacheDataManager.INSTANCE.getTotalCacheSize(this));

        cb_top.setChecked(CacheUtil.getTopArticle());
        try {
            versionCode.setText(getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        clear_cache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showConfirmDialog(SettingsActivity.this, "确定清除缓存吗？", new DialogUtil.ConfirmListener() {
                    @Override
                    public void onConfirm() {
                        CacheDataManager.INSTANCE.clearAllCache(SettingsActivity.this);
                        cache_size.setText("0.0Byte");
                    }
                });
            }
        });


        cb_top.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CacheUtil.setTopArticle(isChecked);
                new SettingEvent(isChecked).post();
            }
        });

        ll_theme_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MaterialDialog(SettingsActivity.this, MaterialDialog.getDEFAULT_BEHAVIOR())
                        .cornerRadius(1f, 3)
                        .show(new Function1<MaterialDialog, Unit>() {
                            @SuppressLint("CheckResult")
                            @Override
                            public Unit invoke(MaterialDialog dialog) {
                                dialog.setTitle("主题颜色");
                                DialogColorChooserExtKt.colorChooser(dialog, ColorUtil.INSTANCE.getACCENT_COLORS(), ColorUtil.INSTANCE.getPRIMARY_COLORS_SUB(),
                                        Utils.getColor(SettingsActivity.this), false, false, false, false, new Function2<MaterialDialog, Integer, Unit>() {
                                            @Override
                                            public Unit invoke(MaterialDialog dialog, Integer integer) {
                                                Utils.setColor(SettingsActivity.this, integer);

                                                return null;
                                            }
                                        });
                                return null;
                            }
                        })
                        .positiveButton(R.string.confirm, null, new Function1<MaterialDialog, Unit>() {
                            @Override
                            public Unit invoke(MaterialDialog dialog) {
                                new SettingEvent().post();
                                dialog.dismiss();
                                return null;
                            }
                        })
                        .negativeButton(R.string.cancel, null, new Function1<MaterialDialog, Unit>() {
                            @Override
                            public Unit invoke(MaterialDialog dialog) {
                                dialog.dismiss();
                                return null;
                            }
                        });
            }
        });


        ll_web.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpWebUtils.startWebActivity(SettingsActivity.this, "玩Android官网", "https://www.wanandroid.com");
            }
        });

        ll_sourceCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpWebUtils.startWebActivity(SettingsActivity.this, "伟子时代的github", "https://github.com/weizi-era/wanandroid_mvp");
            }
        });

        ll_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, DownLoadActivity.class));
            }
        });

        ll_version.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("正在检测中...");
                PgyerSDKManager.checkVersionUpdate(SettingsActivity.this, new CheckoutCallBack() {
                    @Override
                    public void onNewVersionExist(CheckSoftModel checkSoftModel) {

                        ToastUtils.showShort("有新版本更新");
                        String updateDescription = checkSoftModel.buildUpdateDescription;
                        new AlertDialog.Builder(SettingsActivity.this)
                                .setTitle("发现新版本")
                                .setTitle(String.format("是否升级到%s版本？", checkSoftModel.buildVersion))
                                .setMessage(updateDescription)
                                .setPositiveButton("升级", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        ToastUtils.showShort("点击了更新");
                                        dialog.dismiss();
                                    }
                                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                    }

                    @Override
                    public void onNonentityVersionExist(String s) {
                        ToastUtils.showShort("当前版本已最新");
                    }

                    @Override
                    public void onFail(String s) {
                        ToastUtils.showShort("检测版本更新失败");
                    }
                });
            }
        });
    }
}