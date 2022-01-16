package com.zjw.wanandroid_mvp.ui.system;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.mvp.IPresenter;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.VPCommonAdapter;
import com.zjw.wanandroid_mvp.base.BaseFragment;
import com.zjw.wanandroid_mvp.bean.SystemBean;
import com.zjw.wanandroid_mvp.bean.TreeBean;
import com.zjw.wanandroid_mvp.contract.system.SystemContract;
import com.zjw.wanandroid_mvp.presenter.system.SystemPresenter;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class SystemFragment extends BaseFragment<IPresenter> {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<SupportFragment> mFragmentSparseArray = new ArrayList<>();
    private List<String> tabName = new ArrayList<>();

    private VPCommonAdapter vpCommonAdapter;

    @Override
    public void setupFragmentComponent(@NonNull @NotNull AppComponent appComponent) {

    }

    @Override
    public View initView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_system, container, false);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        mFragmentSparseArray.add(new SystemListFragment());
        mFragmentSparseArray.add(new NaviListFragment());
        vpCommonAdapter = new VPCommonAdapter(getChildFragmentManager(), mFragmentSparseArray, tabName);
        viewPager.setAdapter(vpCommonAdapter);
        viewPager.setOffscreenPageLimit(mFragmentSparseArray.size());
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        tabName.add("体系");
        tabName.add("导航");
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(tabName.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(tabName.get(1)));
    }
}
