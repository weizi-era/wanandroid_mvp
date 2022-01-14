package com.zjw.wanandroid_mvp.ui.main.collect;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.base.BaseFragment;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.contract.collect.CollectArticleContract;
import com.zjw.wanandroid_mvp.di.component.collect.DaggerCollectArticleComponent;
import com.zjw.wanandroid_mvp.di.module.collect.CollectArticleModule;
import com.zjw.wanandroid_mvp.event.CollectEvent;
import com.zjw.wanandroid_mvp.presenter.collect.CollectArticlePresenter;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.ToastUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CollectArticleFragment extends BaseFragment<CollectArticlePresenter> implements CollectArticleContract.ICollectArticleView {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;


    private int initPage = 0;
    private int currentPage = initPage;

    private LoadService loadService;

    private ArticleAdapter articleAdapter;

    ImageView mCollection;


    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerCollectArticleComponent.builder()
                .appComponent(appComponent)
                .collectArticleModule(new CollectArticleModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collection_article, container, false);
        loadService = LoadSir.getDefault().register(rootView.findViewById(R.id.swipeRefreshLayout), new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                Utils.setLoadingColor(loadService);
                loadService.showCallback(LoadingCallback.class);
                currentPage = initPage;
                mPresenter.getArticleList(currentPage);
            }
        });

        return rootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAdapter();

        mPresenter.getArticleList(currentPage);
    }


    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        articleAdapter = new ArticleAdapter();
        recyclerView.setAdapter(articleAdapter);

        articleAdapter.addChildClickViewIds(R.id.iv_collection);
        articleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean bean = (ArticleBean) adapter.getItem(position);
                JumpWebUtils.startWebActivity(mContext, bean.getTitle(), bean.getLink());
            }
        });

        articleAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean bean = (ArticleBean) adapter.getItem(position);
                mPresenter.unCollect(bean.getId(), bean.getOriginId(), position);
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        recyclerView.setAdapter(articleAdapter);
        loadService.showCallback(LoadingCallback.class);
        mPresenter.getArticleList(currentPage);
    }

    @Override
    public void showArticleList(BasePageBean<List<ArticleBean>> bean) {
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
    public void unCollect(int position) {
        new CollectEvent(false, articleAdapter.getItem(position).getOriginId()).post();
        if (articleAdapter.getData().size() > 1) {
            articleAdapter.removeAt(position);
        } else {
            loadService.showCallback(EmptyCallback.class);
        }
    }
}