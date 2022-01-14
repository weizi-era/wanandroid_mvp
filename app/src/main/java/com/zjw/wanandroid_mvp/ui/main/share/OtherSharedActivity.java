package com.zjw.wanandroid_mvp.ui.main.share;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.jess.arms.di.component.AppComponent;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.ShareCommonAdapter;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.SharedBean;
import com.zjw.wanandroid_mvp.base.BaseActivity;

import com.zjw.wanandroid_mvp.contract.share.OtherShareContract;
import com.zjw.wanandroid_mvp.di.component.share.DaggerOtherSharedComponent;
import com.zjw.wanandroid_mvp.di.module.share.OtherSharedModule;
import com.zjw.wanandroid_mvp.event.CollectEvent;
import com.zjw.wanandroid_mvp.presenter.share.OtherSharePresenter;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.jetbrains.annotations.NotNull;


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

    private int initPage = 1;
    private int mCurrentPage = initPage;
    private int id;

    private ShareCommonAdapter otherSharedAdapter;

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
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        username.setText(intent.getStringExtra("username"));
        userId.setText("ID：" + id);
        score.setText("积分：" + intent.getIntExtra("score", 0));
        rank.setText("排名：" + intent.getStringExtra("rank"));

        initAdapter();

        // 绑定loadsir
        loadService = LoadSir.getDefault().register(refreshLayout, (Callback.OnReloadListener) v -> {
            Utils.setLoadingColor(loadService);
            loadService.showCallback(LoadingCallback.class);
            mCurrentPage = initPage;
            mPresenter.getOtherShareArticle(id, mCurrentPage);
        });

        mPresenter.getOtherShareArticle(id, mCurrentPage);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        otherSharedAdapter = new ShareCommonAdapter(R.layout.item_home);
        recyclerView.setAdapter(otherSharedAdapter);

        otherSharedAdapter.addChildClickViewIds(R.id.iv_collection, R.id.rl_content);
//        otherSharedAdapter.setOnItemClickListener(new OnItemClickListener() {
//            @Override
//            public void onItemClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
//                SharedBean.Data.ShareArticles.Datas data = (SharedBean.Data.ShareArticles.Datas) adapter.getItem(position);
//
//                JumpWebUtils.startWebActivity(OtherSharedActivity.this, articleDB.title, articleDB.link);
//            }
//        });

        otherSharedAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean data = (ArticleBean) adapter.getItem(position);

                switch (view.getId()) {
                    case R.id.rl_content:
                        JumpWebUtils.startWebActivity(OtherSharedActivity.this, data.getTitle(), data.getLink());
                        break;
                    case R.id.iv_collection:
                        if (data.isCollect()) {
                            mPresenter.unCollect(data.getId(), position);
                        } else {
                            mPresenter.collect(data.getId(), position);
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
        if (mCurrentPage == initPage && bean.getShareArticles().getDatas().size() == 0) {
            loadService.showCallback(EmptyCallback.class);
        } else if (mCurrentPage == initPage) {
            loadService.showSuccess();
            otherSharedAdapter.setNewInstance(bean.getShareArticles().getDatas());
        } else {
            loadService.showSuccess();
            otherSharedAdapter.addData(bean.getShareArticles().getDatas());
        }

        mCurrentPage ++;
        if (bean.getShareArticles().getPageCount() >= mCurrentPage) {
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

    @Override
    public void unCollect(boolean collect, int position) {
        new CollectEvent(collect, otherSharedAdapter.getItem(position).getId()).post();
    }

}