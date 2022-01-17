package com.zjw.wanandroid_mvp.ui.web;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.just.agentweb.AgentWeb;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebViewActivity extends BaseActivity<IPresenter> {

    @BindView(R.id.web_toolbar)
    Toolbar web_toolbar;
    @BindView(R.id.web_content)
    LinearLayout web_content;
    @BindView(R.id.webTitle)
    TextView webTitle;

    private String mTitle;
    private String mUrl;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_webview;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mUrl = intent.getStringExtra("url");

        initToolbar(mTitle);
        AgentWeb.with(this)
                .setAgentWebParent(web_content, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    private void initToolbar(String title) {
        getWindow().setStatusBarColor(Utils.getColor(this));
        web_toolbar.setBackgroundColor(Utils.getColor(this));
        setSupportActionBar(web_toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeAsUpIndicator(R.mipmap.whiteback_icon);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            webTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
            webTitle.setSingleLine(true);
            webTitle.setSelected(true);
            webTitle.setFocusable(true);
            webTitle.setFocusableInTouchMode(true);
            webTitle.setText(title);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_webview, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.item_shared:
                doShared();
                break;
            case R.id.item_useChrome:
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl)));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 分享
     */
    private void doShared() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.app_title_url,
                getString(R.string.app_name), mTitle, mUrl));
        intent.setType("text/plain");
        startActivity(intent);
    }

    /**
     * 利用反射机制调用MenuBuilder的setOptionalIconsVisible方法设置mOptionalIconsVisible为true，
     * 给菜单设置图标时才可见 让菜单同时显示图标和文字
     */
    @Override
    public boolean onMenuOpened(int featureId, Menu menu) {
        if (menu != null) {
            if (menu.getClass().getSimpleName().equalsIgnoreCase("MenuBuilder")) {
                try {
                    Method method = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    method.setAccessible(true);
                    method.invoke(menu, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return super.onMenuOpened(featureId, menu);
    }


}
