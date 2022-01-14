package com.zjw.wanandroid_mvp.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jess.arms.di.component.AppComponent;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.newcaoguo.easyrollingnumber.view.ScrollingDigitalAnimation;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.IntegralAdapter;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.bean.ScoreListBean;
import com.zjw.wanandroid_mvp.di.component.main.DaggerIntegralComponent;
import com.zjw.wanandroid_mvp.di.module.main.IntegralModule;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.main.IntegralContract;
import com.zjw.wanandroid_mvp.presenter.main.IntegralPresenter;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.RecyclerUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

public class IntegralActivity extends BaseActivity<IntegralPresenter> implements IntegralContract.IIntegralView {

    @BindView(R.id.score_number)
    ScrollingDigitalAnimation mScore;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;

    private int initPage = 1;
    private int currentPage = initPage;

    private IntegralAdapter adapter;
    private LoadService loadService;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerIntegralComponent.builder()
                .appComponent(appComponent)
                .integralModule(new IntegralModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_integral_avtivity;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbar.setTitle("我的积分");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mScore.setNumberString(intent.getStringExtra("score"));
        mScore.setDuration(2000);

        loadService = LoadSir.getDefault().register(refreshLayout, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                Utils.setLoadingColor(loadService);
                loadService.showCallback(LoadingCallback.class);
                currentPage = initPage;
                mPresenter.getScoreList(currentPage);
            }
        });

        initAdapter();

        //初始化 swipeRefreshLayout
        refreshLayout.setColorSchemeColors(Utils.getColor(this));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = initPage;
                mPresenter.getScoreList(currentPage);
            }
        });

        //初始化recyclerview
        RecyclerUtil.initRecyclerView(this, recyclerView, new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getScoreList(currentPage);
            }
        });

        mPresenter.getScoreList(currentPage);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new IntegralAdapter(R.layout.item_integral);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean useEventBus() {
        return false;
    }


    @Override
    public void showScoreList(BasePageBean<List<ScoreListBean>> bean) {
        if (currentPage == initPage && bean.getDatas().size() == 0) {
            loadService.showCallback(EmptyCallback.class);
        } else if (currentPage == initPage) {
            loadService.showSuccess();
            adapter.setNewInstance(bean.getDatas());
        } else {
            loadService.showSuccess();
            adapter.addData(bean.getDatas());
        }

        currentPage ++;
        if (bean.getPageCount() >= currentPage) {
            recyclerView.loadMoreFinish(false, true);
        } else {
            // 没有更多数据
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.loadMoreFinish(false, false);
                }
            }, 200);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.menu_score, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                JumpWebUtils.startWebActivity(IntegralActivity.this, getString(R.string.scoreRules), Constant.SCORE_RULE_URL);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}