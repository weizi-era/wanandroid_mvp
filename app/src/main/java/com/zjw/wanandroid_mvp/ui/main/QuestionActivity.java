package com.zjw.wanandroid_mvp.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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

import com.zjw.wanandroid_mvp.di.component.main.DaggerQuestionComponent;
import com.zjw.wanandroid_mvp.di.module.main.QuestionModule;
import com.zjw.wanandroid_mvp.event.CollectEvent;
import com.zjw.wanandroid_mvp.contract.main.QuestionContract;
import com.zjw.wanandroid_mvp.presenter.main.QuestionPresenter;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.RecyclerUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.CollectView;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

public class QuestionActivity extends BaseActivity<QuestionPresenter> implements QuestionContract.IQuestionView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;


    private int initPage = 1;
    private int currentPage = initPage;

    private ArticleAdapter questionAdapter;
    private LoadService loadService;

    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerQuestionComponent.builder()
                .appComponent(appComponent)
                .questionModule(new QuestionModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_question;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        toolbar.setTitle("每日一问");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        loadService = LoadSir.getDefault().register(refreshLayout, (Callback.OnReloadListener) v -> {
            loadService.showCallback(LoadingCallback.class);
            currentPage = initPage;
            mPresenter.getQuestionList(currentPage);
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
                mPresenter.getQuestionList(currentPage);
            }
        });

        //初始化recyclerview
        RecyclerUtil.initRecyclerView(this, recyclerView, new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getQuestionList(currentPage);
            }
        });

        mPresenter.getQuestionList(currentPage);

    }


    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        questionAdapter = new ArticleAdapter();
        recyclerView.setAdapter(questionAdapter);

        questionAdapter.addChildClickViewIds(R.id.iv_collection);
        questionAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean bean = (ArticleBean) adapter.getItem(position);
                JumpWebUtils.startWebActivity(QuestionActivity.this, bean.getTitle(), bean.getLink());
            }
        });

        questionAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean bean = (ArticleBean) adapter.getItem(position);
                CollectView mCollection = view.findViewById(R.id.iv_collection);
                if (mCollection.isChecked()) {
                    mCollection.setImageResource(R.mipmap.star_default);
                    mCollection.setChecked(false);
                    mPresenter.unCollect(bean.getId(), position);
                } else {
                    mCollection.setImageResource(R.mipmap.star_collected);
                    mCollection.setChecked(true);
                    mPresenter.collect(bean.getId(), position);
                }
            }
        });
    }

    @Override
    public void showQuestionList(BasePageBean<List<ArticleBean>> bean) {
        refreshLayout.setRefreshing(false);
        if (currentPage == initPage && bean.getDatas().size() == 0) {
            loadService.showCallback(EmptyCallback.class);
        } else if (currentPage == initPage) {
            loadService.showSuccess();
            questionAdapter.setNewInstance(bean.getDatas());
        } else {
            loadService.showSuccess();
            questionAdapter.addData(bean.getDatas());
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
        new CollectEvent(collect, questionAdapter.getItem(position).getId()).post();
    }
}