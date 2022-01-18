package com.zjw.wanandroid_mvp.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.utils.CacheDataManager;
import com.zjw.wanandroid_mvp.utils.DialogUtil;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

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
    }
}