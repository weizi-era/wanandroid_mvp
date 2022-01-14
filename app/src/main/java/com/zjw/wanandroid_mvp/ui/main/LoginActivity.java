package com.zjw.wanandroid_mvp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.jess.arms.di.component.AppComponent;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.bean.UserBean;
import com.zjw.wanandroid_mvp.contract.main.LoginContract;
import com.zjw.wanandroid_mvp.di.component.main.DaggerLoginComponent;
import com.zjw.wanandroid_mvp.di.module.main.LoginModule;
import com.zjw.wanandroid_mvp.event.LoginEvent;
import com.zjw.wanandroid_mvp.presenter.main.LoginPresenter;
import com.zjw.wanandroid_mvp.utils.CacheUtil;
import com.zjw.wanandroid_mvp.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.ILoginView {

    private String username;
    private String password;
    private String nickName;

    @BindView(R.id.et_name)
    EditText mName;
    @BindView(R.id.et_pwd)
    EditText mPwd;
    @BindView(R.id.iv_name)
    ImageView mClear;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.iv_pwd)
    ImageView isShowPwd;
    @BindView(R.id.tv_register)
    TextView mRegister;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    boolean isShow = false;
    boolean isLogin;


    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerLoginComponent.builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_login;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mToolbar.setTitle("登录");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mClear.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s)) {
                    mClear.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void loginSuccess(UserBean bean) {
        CacheUtil.setUserInfo(bean);
        new LoginEvent(true, bean.getCollectIds()).post();
        finish();
    }

    @OnClick(R.id.bt_login)
    public void login() {
        if (TextUtils.isEmpty(mName.getText()) || TextUtils.isEmpty(mPwd.getText())) {
            ToastUtil.show(LoginActivity.this, "请输入用户名或密码");
            return;
        }
        mPresenter.login(mName.getText().toString(), mPwd.getText().toString());
    }

    @OnClick(R.id.iv_name)
    public void clear() {
        if (mName.getText() != null) {
            mName.setText(null);
            mClear.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_register)
    public void goRegister() {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        LoginActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.iv_pwd)
    public void setIsShowPwd() {
        if (!isShow) {
            isShowPwd.setImageResource(R.drawable.pwd_show);
            mPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isShow = true;
        } else {
            isShowPwd.setImageResource(R.drawable.pwd_hide);
            mPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isShow = false;
        }
        mPwd.setSelection(mPwd.getText().length());
    }

}