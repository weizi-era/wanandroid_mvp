package com.zjw.wanandroid_mvp.ui.main;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.jess.arms.di.component.AppComponent;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.RankListAdapter;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.bean.RankListBean;
import com.zjw.wanandroid_mvp.contract.main.RankListContract;
import com.zjw.wanandroid_mvp.di.component.main.DaggerRankListComponent;
import com.zjw.wanandroid_mvp.di.module.main.RankListModule;
import com.zjw.wanandroid_mvp.presenter.main.RankListPresenter;
import com.zjw.wanandroid_mvp.ui.main.share.OtherSharedActivity;
import com.zjw.wanandroid_mvp.utils.RecyclerUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

public class RankActivity extends BaseActivity<RankListPresenter> implements RankListContract.IRankListView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ll_integral)
    LinearLayout linearLayout;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.username)
    TextView username;
    @BindView(R.id.rank)
    TextView rank;
    @BindView(R.id.score)
    TextView score;


    private int initPage = 1;
    private int currentPage = initPage;
    private LoadService loadService;

    private RankListAdapter adapter;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerRankListComponent.builder()
                .appComponent(appComponent)
                .rankListModule(new RankListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_rank;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        username.setText(intent.getStringExtra("username"));
        rank.setText(intent.getStringExtra("rank"));
        score.setText(intent.getStringExtra("score"));
        toolbar.setTitle("积分排行榜");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadService = LoadSir.getDefault().register(linearLayout, new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showCallback(LoadingCallback.class);
                currentPage = initPage;
                mPresenter.getRankList(currentPage);
            }
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
                mPresenter.getRankList(currentPage);
            }
        });

        //初始化recyclerview
        RecyclerUtil.initRecyclerView(this, recyclerView, new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getRankList(currentPage);
            }
        });

        mPresenter.getRankList(initPage);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RankListAdapter(R.layout.item_rank);
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                RankListBean bean = (RankListBean) adapter.getItem(position);
                Intent intent = new Intent(RankActivity.this, OtherSharedActivity.class);
                intent.putExtra("id", bean.getUserId());
                intent.putExtra("username", bean.getUsername());
                intent.putExtra("score", bean.getCoinCount());
                intent.putExtra("rank", bean.getRank());
                startActivity(intent);
            }
        });
    }

    @Override
    public void showRankList(BasePageBean<List<RankListBean>> bean) {

        refreshLayout.setRefreshing(false);
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
}