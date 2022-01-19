package com.zjw.wanandroid_mvp.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jess.arms.di.component.AppComponent;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.ArticleAdapter;
import com.zjw.wanandroid_mvp.base.BaseFragment;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BannerBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.contract.home.HomeContract;
import com.zjw.wanandroid_mvp.di.component.home.DaggerHomeComponent;
import com.zjw.wanandroid_mvp.di.module.home.HomeModule;
import com.zjw.wanandroid_mvp.event.CollectEvent;
import com.zjw.wanandroid_mvp.event.LoginEvent;
import com.zjw.wanandroid_mvp.event.SettingEvent;
import com.zjw.wanandroid_mvp.presenter.home.HomePresenter;
import com.zjw.wanandroid_mvp.ui.main.share.*;
import com.zjw.wanandroid_mvp.ui.main.AuthorInfoActivity;
import com.zjw.wanandroid_mvp.ui.square.PublishActivity;
import com.zjw.wanandroid_mvp.utils.CacheUtil;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.RecyclerUtil;
import com.zjw.wanandroid_mvp.utils.ToastUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.CollectView;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.IHomeView {

    private Banner mBanner;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.floating_action_btn)
    FloatingActionButton floatingActionButton;

    private LoadService loadService;

    private int initPage = 0;
    private int currentPage = initPage;

    private ArticleAdapter homeAdapter;

    private List<ArticleBean> topList;


    @Override
    public void setupFragmentComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerHomeComponent.builder()
                .appComponent(appComponent)
                .homeModule(new HomeModule(this))
                .build()
                .inject(this);
    }

    @Override
    public boolean useEventBus() {
        return true;
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        loadService = LoadSir.getDefault().register(rootView.findViewById(R.id.swipeRefreshLayout), new Callback.OnReloadListener() {
            @Override
            public void onReload(View v) {
                loadService.showCallback(LoadingCallback.class);
                currentPage = initPage;
                mPresenter.loadBanner();
                mPresenter.getHomeArticle(currentPage);
            }
        });

        setHasOptionsMenu(true);
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
                currentPage = initPage;
                mPresenter.getHomeArticle(currentPage);
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
                mPresenter.getHomeArticle(currentPage);
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


    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        homeAdapter = new ArticleAdapter();
        View header = LayoutInflater.from(mContext).inflate(R.layout.head_banner, null, false);
        mBanner = header.findViewById(R.id.home_banner);
        homeAdapter.setHeaderView(header);
        recyclerView.setAdapter(homeAdapter);

        homeAdapter.addChildClickViewIds(R.id.iv_collection);
        homeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean bean = (ArticleBean) adapter.getItem(position);
                JumpWebUtils.startWebActivity(mContext, bean.getTitle(), bean.getLink());
            }
        });

        homeAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean bean = (ArticleBean) adapter.getItem(position);
                CollectView mCollection = view.findViewById(R.id.iv_collection);
                switch (view.getId()) {
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
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void showBanner(List<BannerBean> beanList) {
        mBanner.setAdapter(new BannerImageAdapter<BannerBean>(beanList) {
                @Override
                public void onBindView(BannerImageHolder holder, BannerBean data, int position, int size) {
                    Glide.with(holder.itemView)
                            .load(data.getImagePath())
                            .into(holder.imageView);
                }
            })
                    .addBannerLifecycleObserver(this)
                    .setIndicator(new CircleIndicator(mContext))
                    .setIndicatorGravity(IndicatorConfig.Direction.RIGHT)
                    .start();

            List<String> titles = beanList.stream().map(BannerBean::getTitle)
                    .collect(Collectors.toList());

            List<String> urls = beanList.stream().map(BannerBean::getUrl)
                    .collect(Collectors.toList());

            mBanner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(Object data, int position) {
                    JumpWebUtils.startWebActivity(mContext, titles.get(position), urls.get(position));
                }
            });
    }

    @Override
    public void showHomeArticle(BasePageBean<List<ArticleBean>> bean) {
        refreshLayout.setRefreshing(false);
        if (currentPage == initPage && bean.getDatas().size() == 0) {
            loadService.showCallback(EmptyCallback.class);
        } else if (currentPage == initPage) {
            loadService.showSuccess();
            homeAdapter.setNewInstance(bean.getDatas());
        } else {
            loadService.showSuccess();
            homeAdapter.addData(bean.getDatas());
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


        topList = homeAdapter.getData().subList(0, 6);

    }

    /**
     * 懒加载
     * @param savedInstanceState
     */
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadService.showCallback(LoadingCallback.class);
        recyclerView.setAdapter(homeAdapter);
        mPresenter.loadBanner();
        mPresenter.getHomeArticle(currentPage);
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
    public void collect(boolean collect, int position) {
        new CollectEvent(collect, homeAdapter.getItem(position).getId()).post();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe
    public void freshLogin(@NonNull LoginEvent event) {
        List<ArticleBean> data = homeAdapter.getData();
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
        homeAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe
    public void collectEvent(@NonNull CollectEvent event) {
        List<ArticleBean> data = homeAdapter.getData();
        if (!event.isCollect()) {

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId() == event.getId()) {
                    data.get(i).setCollect(false);
                    break;
                }
            }

            homeAdapter.notifyDataSetChanged();
        }
    }

    @Subscribe
    public void SettingEvent(@NonNull SettingEvent event) {
        List<ArticleBean> data = homeAdapter.getData();

        if (event.isChecked()) {
            data.addAll(0, topList);
        } else {
            data.removeAll(topList);
        }
        homeAdapter.setNewInstance(data);
        homeAdapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent = new Intent(mContext, SearchActivity.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
