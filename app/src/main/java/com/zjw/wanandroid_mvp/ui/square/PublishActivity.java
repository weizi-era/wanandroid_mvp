package com.zjw.wanandroid_mvp.ui.square;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.di.component.AppComponent;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.bean.CoinInfo;
import com.zjw.wanandroid_mvp.bean.UserBean;
import com.zjw.wanandroid_mvp.contract.square.PublishContract;
import com.zjw.wanandroid_mvp.di.component.square.DaggerPublishComponent;
import com.zjw.wanandroid_mvp.di.module.square.PublishModule;
import com.zjw.wanandroid_mvp.event.AddEvent;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.presenter.square.PublishPresenter;
import com.zjw.wanandroid_mvp.utils.CacheUtil;
import com.zjw.wanandroid_mvp.utils.DialogUtil;
import com.zjw.wanandroid_mvp.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class PublishActivity extends BaseActivity<PublishPresenter> implements PublishContract.IPublishView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_article_title)
    EditText articleTitle;
    @BindView(R.id.et_article_link)
    EditText articleLink;
    @BindView(R.id.shared_user)
    TextView shared_user;
    @BindView(R.id.bt_share)
    Button btShare;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerPublishComponent.builder()
                .appComponent(appComponent)
                .publishModule(new PublishModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_publish;
    }

    @Override
    public void initData(@Nullable  Bundle savedInstanceState) {

        shared_user.setText("");

        toolbar.setTitle("????????????");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = articleTitle.getText().toString();
                String link = articleLink.getText().toString();

                if (TextUtils.isEmpty(title)) {
                    ToastUtil.show(PublishActivity.this, "??????????????????");
                    return;
                }
                if (TextUtils.isEmpty(link)) {
                    ToastUtil.show(PublishActivity.this, "??????????????????");
                    return;
                }

                if (!link.startsWith("http://") && !link.startsWith("https://")) {
                    ToastUtil.show(PublishActivity.this, "??????????????? http:// ?????? https:// ?????????");
                    return;
                }

                DialogUtil.showConfirmDialog(PublishActivity.this, "??????????????????", new DialogUtil.ConfirmListener() {
                    @Override
                    public void onConfirm() {
                        mPresenter.addArticle(title, link);
                    }
                });
            }
        });
    }

    @Override
    public boolean useEventBus() {
        return false;
    }


    @Override
    public void addArticle() {
        new AddEvent(Constant.SHARE_CODE).post();
        finish();
    }
}