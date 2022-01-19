package com.zjw.wanandroid_mvp.ui.main.share;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.jess.arms.di.component.AppComponent;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.ArticleAdapter;
import com.zjw.wanandroid_mvp.adapter.ShareCommonAdapter;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.SharedBean;
import com.zjw.wanandroid_mvp.base.BaseActivity;

import com.zjw.wanandroid_mvp.contract.share.OtherShareContract;
import com.zjw.wanandroid_mvp.di.component.share.DaggerOtherSharedComponent;
import com.zjw.wanandroid_mvp.di.module.share.OtherSharedModule;
import com.zjw.wanandroid_mvp.event.CollectEvent;
import com.zjw.wanandroid_mvp.event.LoginEvent;
import com.zjw.wanandroid_mvp.presenter.share.OtherSharePresenter;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.RecyclerUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.CollectView;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;


import java.util.List;

import butterknife.BindView;

public class OtherSharedActivity extends BaseActivity<OtherSharePresenter> implements OtherShareContract.IOtherShareView {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.userId)
    TextView userId;
    @BindView(R.id.score)
    TextView score;
    @BindView(R.id.rank)
    TextView rank;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.header)
    LinearLayout header;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;

    private int initPage = 1;
    private int currentPage = initPage;
    private int id;

    private ArticleAdapter otherSharedAdapter;

    private LoadService loadService;

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_other_shared;
    }

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerOtherSharedComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .otherSharedModule(new OtherSharedModule(this))
                .build()
                .inject(this);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        username.setText(intent.getStringExtra("username"));
        userId.setText("ID：" + id);
        score.setText("积分：" + intent.getIntExtra("score", 0));
        rank.setText("排名：" + intent.getStringExtra("rank"));


        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -header.getHeight() / 2) {
                    collapsingToolbarLayout.setTitle(intent.getStringExtra("username"));
                    collapsingToolbarLayout.setExpandedTitleColor(Color.WHITE);
                    collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);
                } else {
                    collapsingToolbarLayout.setTitle("");
                }
            }
        });


        // 绑定loadsir
        loadService = LoadSir.getDefault().register(refreshLayout, (Callback.OnReloadListener) v -> {
            loadService.showCallback(LoadingCallback.class);
            currentPage = initPage;
            mPresenter.getOtherShareArticle(id, currentPage);
        });


        Utils.setLoadingColor(loadService);
        loadService.showCallback(LoadingCallback.class);

        //初始化 swipeRefreshLayout
        refreshLayout.setColorSchemeColors(Utils.getColor(this));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = initPage;
                mPresenter.getOtherShareArticle(id, currentPage);
            }
        });

        //初始化recyclerview
        RecyclerUtil.initRecyclerView(this, recyclerView, new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getOtherShareArticle(id, currentPage);
            }
        });

        initAdapter();

        mPresenter.getOtherShareArticle(id, currentPage);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        otherSharedAdapter = new ArticleAdapter();
        recyclerView.setAdapter(otherSharedAdapter);

        otherSharedAdapter.addChildClickViewIds(R.id.iv_collection, R.id.rl_content);

        otherSharedAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean bean = (ArticleBean) adapter.getItem(position);
                CollectView mCollection = view.findViewById(R.id.iv_collection);
                switch (view.getId()) {
                    case R.id.rl_content:
                        JumpWebUtils.startWebActivity(OtherSharedActivity.this, bean.getTitle(), bean.getLink());
                        break;
                    case R.id.iv_collection:
                        if (mCollection.isChecked()) {
                            mCollection.setImageResource(R.mipmap.star_default);
                            mCollection.setChecked(false);
                            mPresenter.unCollect(bean.getId(), position);
                        } else {
                            mCollection.setImageResource(R.mipmap.star_collected);
                            mCollection.setChecked(true);
                            mPresenter.collect(bean.getId(), position);
                        }
                        break;
                }
            }
        });
    }

    @Override
    public boolean useEventBus() {
        return false;
    }


    @Override
    public void showArticleList(SharedBean bean) {
        refreshLayout.setRefreshing(false);
        if (currentPage == initPage && bean.getShareArticles().getDatas().size() == 0) {
            loadService.showCallback(EmptyCallback.class);
        } else if (currentPage == initPage) {
            loadService.showSuccess();
            otherSharedAdapter.setNewInstance(bean.getShareArticles().getDatas());
        } else {
            loadService.showSuccess();
            otherSharedAdapter.addData(bean.getShareArticles().getDatas());
        }

        currentPage ++;
        if (bean.getShareArticles().getPageCount() >= currentPage) {
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
        new CollectEvent(collect, otherSharedAdapter.getItem(position).getId()).post();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe
    public void collectEvent(@NonNull CollectEvent event) {
        List<ArticleBean> data = otherSharedAdapter.getData();
        if (!event.isCollect()) {

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId() == event.getId()) {
                    data.get(i).setCollect(false);
                    break;
                }
            }

            otherSharedAdapter.notifyDataSetChanged();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe
    public void freshLogin(@NonNull LoginEvent event) {
        List<ArticleBean> data = otherSharedAdapter.getData();
        if (event.isLogin()) {
            event.getCollectIds().forEach(item -> {
                for (ArticleBean item1 : data) {
                    if (item1.getId() == Integer.parseInt(String.valueOf(item))) {
                        item1.setCollect(true);
                        break;
                    }
                }
            });
        } else {
            for (ArticleBean item1 : data) {
                item1.setCollect(false);
            }
        }
        otherSharedAdapter.notifyDataSetChanged();
    }

}