package com.zjw.wanandroid_mvp.ui.main.share;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.jess.arms.di.component.AppComponent;
import com.kingja.loadsir.LoadSirUtil;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.ShareCommonAdapter;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.SharedBean;
import com.zjw.wanandroid_mvp.contract.share.MyShareContract;
import com.zjw.wanandroid_mvp.di.component.share.DaggerMySharedComponent;
import com.zjw.wanandroid_mvp.di.module.share.MySharedModule;
import com.zjw.wanandroid_mvp.event.AddEvent;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.presenter.share.MySharePresenter;
import com.zjw.wanandroid_mvp.ui.square.PublishActivity;
import com.zjw.wanandroid_mvp.utils.DialogUtil;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.RecyclerUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.SwipeItemLayout;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.jetbrains.annotations.NotNull;

import butterknife.BindView;

public class MySharedActivity extends BaseActivity<MySharePresenter> implements MyShareContract.IMyShareView {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ShareCommonAdapter shareAdapter;

    private LoadService loadService;

    private int initPage = 1;

    private int currentPage = initPage;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {

        DaggerMySharedComponent //??????????????????,?????????????????????
                .builder()
                .appComponent(appComponent)
                .mySharedModule(new MySharedModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_shared;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbar.setTitle("????????????");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // ??????loadSir
        loadService = LoadSir.getDefault().register(refreshLayout, (Callback.OnReloadListener) v -> {
            Utils.setLoadingColor(loadService);
            loadService.showCallback(LoadingCallback.class);
            currentPage = initPage;
            mPresenter.getMyShareArticle(currentPage);
        });

        Utils.setLoadingColor(loadService);
        loadService.showCallback(LoadingCallback.class);

        initAdapter();

        //????????? swipeRefreshLayout
        refreshLayout.setColorSchemeColors(Utils.getColor(MySharedActivity.this));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = initPage;
                mPresenter.getMyShareArticle(currentPage);
            }
        });

        //?????????recyclerview
        RecyclerUtil.initRecyclerView(MySharedActivity.this, recyclerView, new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getMyShareArticle(currentPage);
            }
        });

        mPresenter.getMyShareArticle(currentPage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_square, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull @NotNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                startActivity(new Intent(MySharedActivity.this, PublishActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initAdapter() {

        shareAdapter = new ShareCommonAdapter(R.layout.item_shared);
        recyclerView.setAdapter(shareAdapter);

        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));

        shareAdapter.addChildClickViewIds(R.id.btn_delete, R.id.rl_content);

        shareAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean data = (ArticleBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.rl_content:
                        JumpWebUtils.startWebActivity(MySharedActivity.this, data.getTitle(), data.getLink());
                        break;
                    case R.id.btn_delete:
                        DialogUtil.showConfirmDialog(MySharedActivity.this, "??????????????????", new DialogUtil.ConfirmListener() {
                            @Override
                            public void onConfirm() {
                                mPresenter.deleteMyShareArticle(data.getId(), position);
                            }
                        });
                }
            }
        });
    }

    @Override
    public void showArticleList(SharedBean bean) {
        refreshLayout.setRefreshing(false);
        if (currentPage == initPage && bean.getShareArticles().getDatas().size() == 0) {
            loadService.showCallback(EmptyCallback.class);
        } else if (currentPage == initPage) {
            loadService.showSuccess();
            shareAdapter.setNewInstance(bean.getShareArticles().getDatas());
        } else {
            loadService.showSuccess();
            shareAdapter.addData(bean.getShareArticles().getDatas());
        }

        currentPage ++;
        if (bean.getShareArticles().getPageCount() >= currentPage) {
            recyclerView.loadMoreFinish(false, true);
        } else {
            // ??????????????????
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    recyclerView.loadMoreFinish(false, false);
                }
            }, 200);
        }
    }

    @Override
    public void deleteArticle(int position) {
        shareAdapter.removeAt(position);
        if (shareAdapter.getData().size() == 0) {
            loadService.showCallback(EmptyCallback.class);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(@NonNull AddEvent event) {
        if (event.getCode() == Constant.SHARE_CODE) {
            loadService.showCallback(LoadingCallback.class);
            shareAdapter.setNewInstance(null);
            mPresenter.getMyShareArticle(initPage);
        }
    }
}