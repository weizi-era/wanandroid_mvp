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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.just.agentweb.AgentWeb;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.utils.Utils;

import java.lang.reflect.Method;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WebViewActivity extends AppCompatActivity {

    @BindView(R.id.web_toolbar)
    Toolbar web_toolbar;
    @BindView(R.id.web_content)
    LinearLayout web_content;
    @BindView(R.id.webTitle)
    TextView webTitle;

    private AgentWeb mAgentWeb;
    private String mTitle;
    private String mUrl;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        mContext = getApplicationContext();
        getIntentInfo();
        initToolbar(mTitle);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(web_content, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(mUrl);
    }

    private void initToolbar(String title) {
        getWindow().setStatusBarColor(Utils.getColor(mContext));
        web_toolbar.setBackgroundColor(Utils.getColor(mContext));
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


    private void getIntentInfo() {
        Intent intent = getIntent();
        mTitle = intent.getStringExtra("title");
        mUrl = intent.getStringExtra("url");
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
