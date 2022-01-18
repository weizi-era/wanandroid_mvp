package com.zjw.wanandroid_mvp.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.base.BaseActivity;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class DownLoadActivity extends BaseActivity<IPresenter> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_down_load;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbar.setTitle("扫码下载");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}