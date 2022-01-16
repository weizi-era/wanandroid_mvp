package com.zjw.wanandroid_mvp.ui.system;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.di.component.AppComponent;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.ArticleAdapter;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.di.component.system.DaggerSystemArticleComponent;
import com.zjw.wanandroid_mvp.di.module.system.SystemArticleModule;
import com.zjw.wanandroid_mvp.event.CollectEvent;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.system.SystemArticleContract;
import com.zjw.wanandroid_mvp.event.LoginEvent;
import com.zjw.wanandroid_mvp.presenter.system.SystemArticlePresenter;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.RecyclerUtil;
import com.zjw.wanandroid_mvp.utils.ToastUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class SystemArticleActivity extends BaseActivity<SystemArticlePresenter> implements SystemArticleContract.ISystemArticleView {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    private ArticleAdapter articleAdapter;

    private LoadService loadService;

    private int initPage = 0;
    private int currentPage = initPage;


    private String title;
    private int cid;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerSystemArticleComponent.builder()
                .appComponent(appComponent)
                .systemArticleModule(new SystemArticleModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_system_article;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        cid = intent.getIntExtra("cid", 0);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadService = LoadSir.getDefault().register(refreshLayout, (Callback.OnReloadListener) v -> {
            loadService.showCallback(LoadingCallback.class);
            currentPage = initPage;
            mPresenter.getArticleList(currentPage, cid);
        });

        Utils.setLoadingColor(loadService);

        loadService.showCallback(LoadingCallback.class);

        initAdapter();

        //初始化 swipeRefreshLayout
        refreshLayout.setColorSchemeColors(Utils.getColor(this));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = initPage;
                mPresenter.getArticleList(currentPage, cid);
            }
        });

        //初始化recyclerview
        RecyclerUtil.initRecyclerView(this, recyclerView, new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getArticleList(currentPage, cid);
            }
        });

        mPresenter.getArticleList(currentPage, cid);

    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        articleAdapter = new ArticleAdapter();
        recyclerView.setAdapter(articleAdapter);

        articleAdapter.addChildClickViewIds(R.id.iv_collection);
        articleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean bean = (ArticleBean) adapter.getItem(position);
                JumpWebUtils.startWebActivity(SystemArticleActivity.this, bean.getTitle(), bean.getLink());
            }
        });

        articleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean bean = (ArticleBean) adapter.getItem(position);
                ImageView mCollection = view.findViewById(R.id.iv_collection);
                if (bean.isCollect()) {
                    mCollection.setImageResource(R.mipmap.star_default);
                    mPresenter.unCollect(bean.getId(), position);
                } else {
                    mCollection.setImageResource(R.mipmap.star_collected);
                    mPresenter.collect(bean.getId(), position);
                }
            }
        });
    }

    @Override
    public void showArticleList(BasePageBean<List<ArticleBean>> bean) {
        refreshLayout.setRefreshing(false);
        if (currentPage == initPage && bean.getDatas().size() == 0) {
            loadService.showCallback(EmptyCallback.class);
        } else if (currentPage == initPage) {
            loadService.showSuccess();
            articleAdapter.setNewInstance(bean.getDatas());
        } else {
            loadService.showSuccess();
            articleAdapter.addData(bean.getDatas());
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
    public void collect(boolean collect, int position) {
        new CollectEvent(collect, articleAdapter.getItem(position).getId()).post();
    }
}