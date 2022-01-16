package com.zjw.wanandroid_mvp.ui.project;


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
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.VPCommonAdapter;
import com.zjw.wanandroid_mvp.base.BaseFragment;
import com.zjw.wanandroid_mvp.bean.TreeBean;
import com.zjw.wanandroid_mvp.di.component.project.DaggerProjectTreeComponent;
import com.zjw.wanandroid_mvp.di.module.project.ProjectTreeModule;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.project.ProjectTreeContract;
import com.zjw.wanandroid_mvp.presenter.project.ProjectTreePresenter;
import com.zjw.wanandroid_mvp.utils.CacheUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.callback.ErrorCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

public class ProjectFragment extends BaseFragment<ProjectTreePresenter> implements ProjectTreeContract.IProjectTreeView {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    private List<SupportFragment> mFragmentSparseArray = new ArrayList<>();

    private List<String> tagNameList = new ArrayList<>();

    private LoadService loadService;
    private VPCommonAdapter vpCommonAdapter;


    @Override
    public void setupFragmentComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerProjectTreeComponent.builder()
                .appComponent(appComponent)
                .projectTreeModule(new ProjectTreeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_project, container, false);
        loadService = LoadSir.getDefault().register(rootView.findViewById(R.id.viewPager), new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showCallback(LoadingCallback.class);
                mPresenter.getProjectCategory();
            }
        });

        Utils.setLoadingColor(loadService);
        return rootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        vpCommonAdapter = new VPCommonAdapter(getChildFragmentManager(), mFragmentSparseArray, tagNameList);
        mPresenter.getProjectCategory();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        vpCommonAdapter.notifyDataSetChanged();
        viewPager.setAdapter(vpCommonAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void showProjectCategory(List<TreeBean> bean) {
        if (bean.size() == 0) {
            loadService.showCallback(ErrorCallback.class);
        } else {
            loadService.showSuccess();

            if (mFragmentSparseArray.size() == 0) {
                bean.forEach(data -> {
                    mFragmentSparseArray.add(new ProjectListFragment(data.getId()));
                });
            }

            if (tagNameList.size() == 0) {
                bean.forEach(data -> {
                    tagNameList.add(data.getName());
                    tabLayout.addTab(tabLayout.newTab().setText(data.getName()));
                });
            }
        }

        vpCommonAdapter.notifyDataSetChanged();

        viewPager.setOffscreenPageLimit(mFragmentSparseArray.size());
    }
}
