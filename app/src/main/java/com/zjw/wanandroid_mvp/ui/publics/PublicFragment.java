package com.zjw.wanandroid_mvp.ui.publics;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jess.arms.di.component.AppComponent;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.VPCommonAdapter;
import com.zjw.wanandroid_mvp.base.BaseFragment;
import com.zjw.wanandroid_mvp.bean.TreeBean;
import com.zjw.wanandroid_mvp.contract.publics.PublicTreeContract;
import com.zjw.wanandroid_mvp.di.component.project.DaggerProjectTreeComponent;
import com.zjw.wanandroid_mvp.di.component.publics.DaggerPublicTreeComponent;
import com.zjw.wanandroid_mvp.di.module.project.ProjectTreeModule;
import com.zjw.wanandroid_mvp.di.module.publics.PublicTreeModule;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.presenter.publics.PublicTreePresenter;
import com.zjw.wanandroid_mvp.utils.CacheUtil;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class PublicFragment extends BaseFragment<PublicTreePresenter> implements PublicTreeContract.IPublicTreeView {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private ArrayList<Fragment> mFragmentSparseArray = new ArrayList<>();

    private List<TreeBean> publicTagList = new ArrayList<>();

    private List<String> tagNameList = new ArrayList<>();

    @Override
    public void setupFragmentComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerPublicTreeComponent.builder()
                .appComponent(appComponent)
                .publicTreeModule(new PublicTreeModule(this))
                .build()
                .inject(this);

    }

    @Override
    public View initView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_public, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
       // publicTagList = CacheUtil.getPublicTagCache();

        viewPager.setOffscreenPageLimit(2);

//        if (publicTagList != null) {
//            for (TreeBean data : publicTagList) {
//                tagNameList.add(data.getName());
//                tabLayout.addTab(tabLayout.newTab().setText(data.getName()));
//            }
//            viewPager.setAdapter(new VPCommonAdapter(getChildFragmentManager(), mFragmentSparseArray, tagNameList));
//        } else {
//            mPresenter.getPublicCategory();
//        }

        mPresenter.getPublicCategory();

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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void showPublicCategory(List<TreeBean> bean) {
        bean.forEach(data -> {
            tagNameList.add(data.getName());
            tabLayout.addTab(tabLayout.newTab().setText(data.getName()));
            PublicListFragment publicListFragment = new PublicListFragment(data.getId());
            mFragmentSparseArray.add(publicListFragment);
        });

        viewPager.setAdapter(new VPCommonAdapter(getChildFragmentManager(), mFragmentSparseArray, tagNameList));
    }

    @Override
    public boolean useEventBus() {
        return false;
    }
}
