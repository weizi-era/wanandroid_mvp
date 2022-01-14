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
import com.zjw.wanandroid_mvp.presenter.share.MySharePresenter;
import com.zjw.wanandroid_mvp.ui.square.PublishActivity;
import com.zjw.wanandroid_mvp.utils.DialogUtil;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.RecyclerUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.SpaceItemDecoration;
import com.zjw.wanandroid_mvp.widget.SwipeItemLayout;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.greenrobot.eventbus.EventBus;
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

    ImageView mCollection;

    private int initPage = 1;

    private int currentPage = initPage;


    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {

        DaggerMySharedComponent //如找不到该类,请编译一下项目
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
        toolbar.setTitle("我的分享");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 绑定loadSir
        loadService = LoadSir.getDefault().register(refreshLayout, (Callback.OnReloadListener) v -> {
            Utils.setLoadingColor(loadService);
            loadService.showCallback(LoadingCallback.class);
            currentPage = initPage;
            mPresenter.getMyShareArticle(currentPage);
        });


        initAdapter();

        //初始化 swipeRefreshLayout
        refreshLayout.setColorSchemeColors(Utils.getColor(MySharedActivity.this));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = initPage;
                mPresenter.getMyShareArticle(currentPage);
            }
        });

        //初始化recyclerview
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

        recyclerView.addItemDecoration(new SpaceItemDecoration(this));
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
                        DialogUtil.showConfirmDialog(MySharedActivity.this, "确定删除吗？", new DialogUtil.ConfirmListener() {
                            @Override
                            public void onConfirm() {
                                mPresenter.deleteMyShareArticle(data.getId(), position);
                                adapter.removeAt(position);
                            }
                        });
                }
            }
        });
    }

    @Override
    public void showArticleList(SharedBean bean) {
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
    public void deleteArticle(int position) {
        shareAdapter.removeAt(position);

        EventBus.getDefault().post(this);
        if (shareAdapter.getData().size() == 0) {
            currentPage = initPage;
            mPresenter.deleteMyShareArticle(currentPage,position);
        }
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    public void onEvent(AddEvent event) {
//        if (event.success()) {
//            mPresenter.getMyShareArticle(initPage);
//        }
//    }
}