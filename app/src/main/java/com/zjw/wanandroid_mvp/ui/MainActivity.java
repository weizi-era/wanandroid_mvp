package com.zjw.wanandroid_mvp.ui;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.jess.arms.di.component.AppComponent;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.bean.CoinInfo;
import com.zjw.wanandroid_mvp.bean.UserBean;
import com.zjw.wanandroid_mvp.contract.main.MainContract;
import com.zjw.wanandroid_mvp.di.component.main.DaggerMainComponent;
import com.zjw.wanandroid_mvp.di.module.main.MainModule;
import com.zjw.wanandroid_mvp.event.LoginEvent;
import com.zjw.wanandroid_mvp.presenter.main.MainPresenter;
import com.zjw.wanandroid_mvp.ui.main.collect.CollectArticleActivity;
import com.zjw.wanandroid_mvp.ui.main.IntegralActivity;
import com.zjw.wanandroid_mvp.ui.main.LoginActivity;
import com.zjw.wanandroid_mvp.ui.main.QuestionActivity;
import com.zjw.wanandroid_mvp.ui.main.RankActivity;
import com.zjw.wanandroid_mvp.ui.main.SettingsActivity;
import com.zjw.wanandroid_mvp.ui.main.todo.TodoActivity;
import com.zjw.wanandroid_mvp.ui.main.share.MySharedActivity;
import com.zjw.wanandroid_mvp.ui.home.HomeFragment;
import com.zjw.wanandroid_mvp.ui.project.ProjectFragment;
import com.zjw.wanandroid_mvp.ui.publics.PublicFragment;
import com.zjw.wanandroid_mvp.ui.square.SquareFragment;
import com.zjw.wanandroid_mvp.ui.system.SystemFragment;
import com.zjw.wanandroid_mvp.utils.CacheUtil;
import com.zjw.wanandroid_mvp.utils.DialogUtil;
import com.zjw.wanandroid_mvp.utils.ToastUtil;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.IMainView {

    private static final int INDEX_HOME = 0;
    private static final int INDEX_PROJECT = 1;
    private static final int INDEX_SQUARE = 2;
    private static final int INDEX_PUBLIC = 3;
    private static final int INDEX_SYSTEM = 4;

    private int mIndex = INDEX_HOME;

    @BindView(R.id.navigation)
    BottomNavigationView mBottomNavigationView;
    @BindView(R.id.drawer_main)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    private HomeFragment mHomeFragment;
    private SquareFragment mSquareFragment;
    private PublicFragment mPublicFragment;
    private SystemFragment mSystemFragment;
    private ProjectFragment mProjectFragment;

    CircleImageView mUserAvatar;
    TextView mUserName;
    TextView mUserGrade;
    TextView mUserRank;
    ImageView mRankList;

    TextView mUserScore;


    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerMainComponent.builder()
                .appComponent(appComponent)
                .mainModule(new MainModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_main;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        mToolbar.setTitle(getString(R.string.app_name));

        setSupportActionBar(mToolbar);

        initBottomNavigation();

        initDrawerLayout();

        initNaviView();

        showFragment(mIndex);
    }


    private void initNaviView() {
        mUserAvatar = mNavigationView.getHeaderView(0).findViewById(R.id.profile_image);
        mUserName = mNavigationView.getHeaderView(0).findViewById(R.id.tv_username);
        mUserGrade = mNavigationView.getHeaderView(0).findViewById(R.id.tv_user_grade);
        mUserRank = mNavigationView.getHeaderView(0).findViewById(R.id.tv_user_rank);
        mRankList = mNavigationView.getHeaderView(0).findViewById(R.id.iv_rank);
        mUserScore = (TextView) mNavigationView.getMenu().findItem(R.id.integral).getActionView();
        mUserScore.setGravity(Gravity.CENTER_VERTICAL);
        mNavigationView.getMenu().findItem(R.id.logout).setVisible(CacheUtil.isLogin());

        mRankList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RankActivity.class);
                intent.putExtra("rank", mUserRank.getText().toString());
                intent.putExtra("username", mUserName.getText().toString());
                intent.putExtra("score", mUserScore.getText().toString());
                startActivity(intent);
//                if (!CacheUtil.isLogin()) {
//                    ToastUtil.show(MainActivity.this, "请先登录");
//                    goLogin();
//                } else {
//                    Intent intent = new Intent(MainActivity.this, RankActivity.class);
//                    intent.putExtra("rank", mUserRank.getText().toString());
//                    intent.putExtra("username", mUserName.getText().toString());
//                    intent.putExtra("score", mUserScore.getText().toString());
//                    startActivity(intent);
//                }
            }
        });

        mUserName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!CacheUtil.isLogin()) {
                    goLogin();
                }
            }
        });

        if (CacheUtil.isLogin()) {
            UserBean userInfo = CacheUtil.getUserInfo();
            if (userInfo != null) {
                mUserName.setText(userInfo.getNickname().isEmpty() ? userInfo.getUsername() : userInfo.getNickname());
            }
            mPresenter.getUserInfo();
        } else {
            mUserName.setText("去登录");
            mUserGrade.setText("--");
            mUserRank.setText("--");
            mUserScore.setText("");
            mUserAvatar.setImageResource(R.mipmap.default_avatar);
        }

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.integral:
                        if (CacheUtil.isLogin()) {
                            Intent intent = new Intent(MainActivity.this, IntegralActivity.class);
                            intent.putExtra("score", mUserScore.getText().toString());
                            startActivity(intent);
                        } else {
                            ToastUtil.show(MainActivity.this, "请先登录");
                            goLogin();
                        }
                        break;
                    case R.id.collection:
                        if (CacheUtil.isLogin()) {
                            startActivity(new Intent(MainActivity.this, CollectArticleActivity.class));
                        } else {
                            ToastUtil.show(MainActivity.this, "请先登录");
                            goLogin();
                        }
                        break;
                    case R.id.shared:
                        if (CacheUtil.isLogin()) {
                            startActivity(new Intent(MainActivity.this, MySharedActivity.class));
                        } else {
                            ToastUtil.show(MainActivity.this, "请先登录");
                            goLogin();
                        }
                        break;
                    case R.id.todo:
                        if (CacheUtil.isLogin()) {
                            startActivity(new Intent(MainActivity.this, TodoActivity.class));
                        } else {
                            ToastUtil.show(MainActivity.this, "请先登录");
                            goLogin();
                        }
                        break;
                    case R.id.question:
                        startActivity(new Intent(MainActivity.this, QuestionActivity.class));
                        break;
                    case R.id.nightMode:
                        break;
                    case R.id.settings:
                        Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.tv_username: // 登录
                        if (!CacheUtil.isLogin()) {
                            goLogin();
                        }
                        break;
                    case R.id.logout:  // 退出
                        DialogUtil.showConfirmDialog(MainActivity.this, "确认退出登录?", () -> mPresenter.logout());
                        break;
                }

                return true;
            }
        });
    }

    /**
     * 去登录
     */
    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void initDrawerLayout() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                mToolbar, R.string.open, R.string.close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);
    }

    private void initBottomNavigation() {

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_home:
                        showFragment(INDEX_HOME);
                        break;
                    case R.id.menu_project:
                        showFragment(INDEX_PROJECT);
                        break;
                    case R.id.menu_square:
                        showFragment(INDEX_SQUARE);
                        break;
                    case R.id.menu_public:
                        showFragment(INDEX_PUBLIC);
                        break;
                    case R.id.menu_system:
                        showFragment(INDEX_SYSTEM);
                        break;
                }
                return true;
            }
        });
    }

    private void showFragment(int index) {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideFragments(transaction);
        mIndex = index;
        switch (index) {
            case INDEX_HOME:
                mToolbar.setTitle("玩Android");
                if (mHomeFragment == null) {
                    mHomeFragment = new HomeFragment();
                    transaction.add(R.id.container, mHomeFragment, "home");
                } else {
                    transaction.show(mHomeFragment);
                }
                break;
            case INDEX_PROJECT:
                mToolbar.setTitle("项目");
                if (mProjectFragment == null) {
                    mProjectFragment = new ProjectFragment();
                    transaction.add(R.id.container, mProjectFragment, "project");
                } else {
                    transaction.show(mProjectFragment);
                }
                break;
            case INDEX_SQUARE:
                mToolbar.setTitle("广场");
                if (mSquareFragment == null) {
                    mSquareFragment = new SquareFragment();
                    transaction.add(R.id.container, mSquareFragment, "square");
                } else {
                    transaction.show(mSquareFragment);
                }
                break;
            case INDEX_PUBLIC:
                mToolbar.setTitle("公众号");
                if (mPublicFragment == null) {
                    mPublicFragment = new PublicFragment();
                    transaction.add(R.id.container, mPublicFragment, "public");
                } else {
                    transaction.show(mPublicFragment);
                }
                break;
            case INDEX_SYSTEM:
                mToolbar.setTitle("体系");
                if (mSystemFragment == null) {
                    mSystemFragment = new SystemFragment();
                    transaction.add(R.id.container, mSystemFragment, "system");
                } else {
                    transaction.show(mSystemFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void hideFragments(FragmentTransaction transaction) {
        if (mHomeFragment != null) {
            transaction.hide(mHomeFragment);
        }
        if (mSquareFragment != null) {
            transaction.hide(mSquareFragment);
        }
        if (mPublicFragment != null) {
            transaction.hide(mPublicFragment);
        }
        if (mSystemFragment != null) {
            transaction.hide(mSystemFragment);
        }
        if (mProjectFragment != null) {
            transaction.hide(mProjectFragment);
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginEvent event) {
        if (event.isLogin()) {
            UserBean userInfo = CacheUtil.getUserInfo();
            if (userInfo != null) {
                mUserName.setText(userInfo.getNickname().isEmpty() ? userInfo.getUsername() : userInfo.getNickname());
            }
            mNavigationView.getMenu().findItem(R.id.logout).setVisible(true);
            mPresenter.getUserInfo();
        } else {
            mNavigationView.getMenu().findItem(R.id.logout).setVisible(false);
            mUserName.setText("去登录");
            mUserRank.setText("--");
            mUserGrade.setText("--");
            mUserScore.setText("");
            mUserAvatar.setImageResource(R.mipmap.default_avatar);
        }
    }

    /**
     * 显示用户信息（等级、排名、积分）
     * @param bean
     */
    @Override
    public void showUserInfo(CoinInfo bean) {
        mUserGrade.setText(String.valueOf(bean.getLevel()));
        mUserRank.setText(bean.getRank());
        mUserScore.setText(String.valueOf(bean.getCoinCount()));
        mUserAvatar.setImageResource(R.drawable.user_avatar);
    }

    @Override
    public void showLogoutSuccess() {
        CacheUtil.setUserInfo(null);
        new LoginEvent(false).post();
    }
}