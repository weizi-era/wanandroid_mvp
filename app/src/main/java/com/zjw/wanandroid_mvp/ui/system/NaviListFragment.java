package com.zjw.wanandroid_mvp.ui.system;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jess.arms.di.component.AppComponent;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.NaviAdapter;
import com.zjw.wanandroid_mvp.base.BaseFragment;
import com.zjw.wanandroid_mvp.bean.NaviBean;
import com.zjw.wanandroid_mvp.contract.system.NaviContract;
import com.zjw.wanandroid_mvp.di.component.system.DaggerNaviComponent;
import com.zjw.wanandroid_mvp.di.module.system.NaviModule;
import com.zjw.wanandroid_mvp.presenter.system.NaviListPresenter;
import com.zjw.wanandroid_mvp.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.SimpleTabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;

public class NaviListFragment extends BaseFragment<NaviListPresenter> implements NaviContract.INaviView {

    @BindView(R.id.navigation_tab_layout)
    VerticalTabLayout mVerticalTabLayout;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private NaviAdapter naviAdapter;

    private LinearLayoutManager mLinearLayoutManager;


    @Override
    public void setupFragmentComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerNaviComponent.builder()
                .appComponent(appComponent)
                .naviModule(new NaviModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_navi_list, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAdapter();
        mPresenter.getNaviArticleList();

        recyclerView.setNestedScrollingEnabled(false);

        mVerticalTabLayout.setIndicatorColor(Utils.getColor(mContext));
    }

    @Override
    public void showNaviArticleList(List<NaviBean> beans) {
        naviAdapter.setList(beans);

        mVerticalTabLayout.setTabAdapter(new SimpleTabAdapter() {
            @Override
            public int getCount() {
                return beans.size();
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {
                return new ITabView.TabTitle.Builder()
                        .setContent(beans.get(position).getName())
                        .setTextColor(ContextCompat.getColor(mContext, R.color.white),
                                Utils.randomColor())
                        .build();
            }
        });
    }

    private void initAdapter() {
        mLinearLayoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        naviAdapter = new NaviAdapter(R.layout.item_navi_list, getLayoutInflater());
        recyclerView.setAdapter(naviAdapter);

        //滑动流畅
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setHasFixedSize(true);
        leftRightLink();
    }

    /**
     * 左右两边联动
     */
    private void leftRightLink() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                mVerticalTabLayout.setTabSelected(mLinearLayoutManager.findFirstVisibleItemPosition());
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

            }
        });

        mVerticalTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabView tab, int position) {
                mLinearLayoutManager.scrollToPositionWithOffset(position, 0);
            }

            @Override
            public void onTabReselected(TabView tab, int position) {

            }
        });
    }


}
