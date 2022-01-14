package com.zjw.wanandroid_mvp.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
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
import com.zjw.wanandroid_mvp.presenter.home.HomePresenter;
import com.zjw.wanandroid_mvp.ui.main.share.*;
import com.zjw.wanandroid_mvp.ui.main.AuthorInfoActivity;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

import butterknife.BindView;

public class HomeFragment extends BaseFragment<HomePresenter> implements HomeContract.IHomeView {

    private Banner mBanner;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;

    private LoadService loadService;

    private int initPage = 0;
    private int currentPage = initPage;

    private ArticleAdapter homeAdapter;


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

        Utils.setLoadingColor(loadService);

        return rootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAdapter();
    }


    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        homeAdapter = new ArticleAdapter();
        View header = LayoutInflater.from(mContext).inflate(R.layout.head_banner, null, false);
        mBanner = header.findViewById(R.id.home_banner);
        homeAdapter.setHeaderView(header);
        recyclerView.setAdapter(homeAdapter);

        homeAdapter.addChildClickViewIds(R.id.author_name, R.id.iv_collection);
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
                switch (view.getId()) {
                    case R.id.author_name:
                        if (TextUtils.isEmpty(bean.getAuthor())) {
                            Intent intent = new Intent(mContext, OtherSharedActivity.class);
                            intent.putExtra("id", bean.getUserId());
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(mContext, AuthorInfoActivity.class);
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
        Log.d("TAG", "scrollToTop: " + layoutManager.findFirstVisibleItemPosition());
        if (layoutManager.findFirstVisibleItemPosition() > 20) {
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
    public void freshLogin(LoginEvent event) {
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
}
