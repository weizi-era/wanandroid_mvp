package com.zjw.wanandroid_mvp.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

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

import com.jess.arms.di.component.AppComponent;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.bean.UserBean;
import com.zjw.wanandroid_mvp.di.component.main.DaggerRegisterComponent;
import com.zjw.wanandroid_mvp.di.module.main.RegisterModule;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.main.RegisterContract;
import com.zjw.wanandroid_mvp.presenter.main.RegisterPresenter;
import com.zjw.wanandroid_mvp.utils.ToastUtil;

import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.IRegisterView {

    @BindView(R.id.et_name)
    EditText mName;
    @BindView(R.id.et_pwd)
    EditText mPwd;
    @BindView(R.id.et_repwd)
    EditText mRePwd;
    @BindView(R.id.iv_name)
    ImageView mClear;
    @BindView(R.id.bt_register)
    Button mBtRegister;
    @BindView(R.id.iv_pwd)
    ImageView isShowPwd;
    @BindView(R.id.iv_repwd)
    ImageView isReShowPwd;
    @BindView(R.id.tv_goLogin)
    TextView mGoLogin;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    boolean isShow = false;
    boolean isReShow = false;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerRegisterComponent.builder()
                .appComponent(appComponent)
                .registerModule(new RegisterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public int initView(Bundle savedInstanceState) {

        return R.layout.activity_register;


    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mToolbar.setTitle("注册");
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


    @OnClick(R.id.tv_goLogin)
    public void goLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        RegisterActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.bt_register)
    public void register() {
        String name = mName.getText().toString();
        String password = mPwd.getText().toString();
        String repassword = mRePwd.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || TextUtils.isEmpty(repassword)) {
            ToastUtil.show(RegisterActivity.this, "请输入用户名或密码");
            return;
        }
        if (!password.equals(repassword)) {
            ToastUtil.show(RegisterActivity.this, "密码输入不一致");
            return;
        }
        if (password.length() < Constant.PWD_MIN_LENGTH) {
            ToastUtil.show(RegisterActivity.this, "密码长度必须大于6位");
            return;
        }
        mPresenter.register(name, password, repassword);
    }

    @Override
    public void registerResult(UserBean bean) {
        ToastUtil.show(RegisterActivity.this, "注册成功");
        finish();
        RegisterActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

    @OnClick(R.id.iv_repwd)
    public void setReIsShowPwd() {
        if (!isReShow) {
            isReShowPwd.setImageResource(R.drawable.pwd_show);
            mRePwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            isReShow = true;
        } else {
            isReShowPwd.setImageResource(R.drawable.pwd_hide);
            mRePwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
            isReShow = false;
        }
        mRePwd.setSelection(mRePwd.getText().length());

    }
}