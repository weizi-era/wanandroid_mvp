package com.zjw.wanandroid_mvp.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

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
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.contract.home.search.SearchResultContract;
import com.zjw.wanandroid_mvp.di.component.home.search.DaggerSearchResultComponent;
import com.zjw.wanandroid_mvp.di.module.home.search.SearchResultModule;
import com.zjw.wanandroid_mvp.event.CollectEvent;
import com.zjw.wanandroid_mvp.presenter.home.search.SearchResultPresenter;
import com.zjw.wanandroid_mvp.ui.main.AuthorInfoActivity;
import com.zjw.wanandroid_mvp.ui.main.share.OtherSharedActivity;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.RecyclerUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

public class SearchResultActivity extends BaseActivity<SearchResultPresenter> implements SearchResultContract.ISearchResultView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;

    private int initPage = 0;
    private int currentPage = initPage;

    private ArticleAdapter articleAdapter;

    private LoadService loadService;

    ImageView mCollection;
    private boolean isLogin;

    private String key;


    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerSearchResultComponent.builder()
                .appComponent(appComponent)
                .searchResultModule(new SearchResultModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_search_result;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        Intent intent = getIntent();
        key = intent.getStringExtra("key");
        toolbar.setTitle(key);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 绑定loadSir
        loadService = LoadSir.getDefault().register(refreshLayout, (Callback.OnReloadListener) v -> {
            Utils.setLoadingColor(loadService);
            loadService.showCallback(LoadingCallback.class);
            currentPage = initPage;
            mPresenter.getSearchResultList(currentPage, key);
        });


        initAdapter();

        //初始化 swipeRefreshLayout
        refreshLayout.setColorSchemeColors(Utils.getColor(SearchResultActivity.this));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = initPage;
                mPresenter.getSearchResultList(currentPage, key);
            }
        });

        //初始化recyclerview
        RecyclerUtil.initRecyclerView(SearchResultActivity.this, recyclerView, new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getSearchResultList(currentPage, key);
            }
        });

        mPresenter.getSearchResultList(currentPage, key);
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        articleAdapter = new ArticleAdapter();
        recyclerView.setAdapter(articleAdapter);

        articleAdapter.addChildClickViewIds(R.id.author_name, R.id.iv_collection);
        articleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean bean = (ArticleBean) adapter.getItem(position);
                JumpWebUtils.startWebActivity(SearchResultActivity.this, bean.getTitle(), bean.getLink());
            }
        });

        articleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean bean = (ArticleBean) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.author_name:
                        if (TextUtils.isEmpty(bean.getAuthor())) {
                            Intent intent = new Intent(SearchResultActivity.this, OtherSharedActivity.class);
                            intent.putExtra("id", bean.getUserId());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(SearchResultActivity.this, AuthorInfoActivity.class);
                            intent.putExtra("author", bean.getAuthor());
                            startActivity(intent);
                        }
                        break;
                    case R.id.iv_collection:
                        if (bean.isCollect()) {
                            mPresenter.unCollect(bean.getId(), position);
                        } else {
                            mPresenter.collect(bean.getId(), position);
                        }
                }
            }
        });
    }

    @Override
    public void showSearchResultList(BasePageBean<List<ArticleBean>> bean) {
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
        new CollectEvent(collect, articleAdapter.getItem(position).getOriginId()).post();
    }
}