package com.zjw.wanandroid_mvp.ui.main.collect;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.adapter.VPCommonAdapter;
import com.zjw.wanandroid_mvp.di.component.collect.DaggerCollectArticleComponent;
import com.zjw.wanandroid_mvp.di.module.collect.CollectArticleModule;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class CollectArticleActivity extends BaseActivity<IPresenter> {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<SupportFragment> mFragmentSparseArray = new ArrayList<>();
    private List<String> tabName = new ArrayList<>();

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {

    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_collection;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tabName.add("文章");
        tabName.add("网站");

        toolbar.setTitle("我的收藏");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(tabName.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(tabName.get(1)));

        mFragmentSparseArray.add(new CollectArticleFragment());
        mFragmentSparseArray.add(new CollectWebsiteFragment());

        viewPager.setAdapter(new VPCommonAdapter(getSupportFragmentManager(), mFragmentSparseArray, tabName));

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(), false);
            }
        });
    }
}