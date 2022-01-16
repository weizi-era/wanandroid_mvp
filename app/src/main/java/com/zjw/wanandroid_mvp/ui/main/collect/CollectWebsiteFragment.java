package com.zjw.wanandroid_mvp.ui.main.collect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jess.arms.di.component.AppComponent;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.CollectWebsiteAdapter;
import com.zjw.wanandroid_mvp.base.BaseFragment;
import com.zjw.wanandroid_mvp.bean.WebsiteBean;
import com.zjw.wanandroid_mvp.contract.collect.CollectWebsiteContract;
import com.zjw.wanandroid_mvp.di.component.collect.DaggerCollectWebsiteComponent;
import com.zjw.wanandroid_mvp.di.module.collect.CollectWebsiteModule;
import com.zjw.wanandroid_mvp.event.CollectEvent;
import com.zjw.wanandroid_mvp.presenter.collect.CollectWebsitePresenter;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.RecyclerUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

public class CollectWebsiteFragment extends BaseFragment<CollectWebsitePresenter> implements CollectWebsiteContract.ICollectWebsiteView {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.floating_action_btn)
    FloatingActionButton floatingActionButton;

    private LoadService loadService;
    private CollectWebsiteAdapter websiteAdapter;

    @Override
    public void setupFragmentComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerCollectWebsiteComponent.builder()
                .appComponent(appComponent)
                .collectWebsiteModule(new CollectWebsiteModule(this))
                .build()
                .inject(this);
    }


    @Override
    public View initView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_collection_website, container, false);
        loadService = LoadSir.getDefault().register(rootView.findViewById(R.id.swipeRefreshLayout), new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showCallback(LoadingCallback.class);
                mPresenter.getWebsiteList();
            }
        });

        Utils.setLoadingColor(loadService);

        return rootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        initAdapter();

        //初始化 swipeRefreshLayout
        refreshLayout.setColorSchemeColors(Utils.getColor(_mActivity));
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.getWebsiteList();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scrollToTop();
            }
        });

        //初始化recyclerview
        RecyclerUtil.initRecyclerView(_mActivity, recyclerView, new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                mPresenter.getWebsiteList();
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView1, int dx, int dy) {
                super.onScrolled(recyclerView1, dx, dy);
                if (!recyclerView.canScrollVertically(-1)) {
                    floatingActionButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadService.showCallback(LoadingCallback.class);
        recyclerView.setAdapter(websiteAdapter);
        mPresenter.getWebsiteList();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        websiteAdapter = new CollectWebsiteAdapter(R.layout.item_collect_website);
        recyclerView.setAdapter(websiteAdapter);

        websiteAdapter.addChildClickViewIds(R.id.iv_collection);
        websiteAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                WebsiteBean bean = (WebsiteBean) adapter.getItem(position);
                JumpWebUtils.startWebActivity(mContext, bean.getName(), bean.getLink());
            }
        });

        websiteAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                WebsiteBean bean = (WebsiteBean) adapter.getItem(position);
                mPresenter.unCollect(bean.getId(), position);
            }
        });
    }

    public void scrollToTop() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager.findFirstVisibleItemPosition() >= 40) {
            recyclerView.scrollToPosition(0);
        } else {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void showWebsiteList(List<WebsiteBean> bean) {
        refreshLayout.setRefreshing(false);
        if (bean.size() == 0) {
            loadService.showCallback(EmptyCallback.class);
        } else {
            loadService.showSuccess();
            websiteAdapter.setNewInstance(bean);
        }
    }

    @Override
    public void unCollect(int position) {
        new CollectEvent(false, websiteAdapter.getItem(position).getId()).post();
        if (websiteAdapter.getData().size() > 1) {
            websiteAdapter.removeAt(position);
        } else {
            loadService.showCallback(EmptyCallback.class);
        }
    }
}