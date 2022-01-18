package com.zjw.wanandroid_mvp.ui.project;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
import com.zjw.wanandroid_mvp.adapter.ArticleAdapter;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.base.BaseFragment;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.di.component.project.DaggerProjectListComponent;
import com.zjw.wanandroid_mvp.di.module.project.ProjectListModule;
import com.zjw.wanandroid_mvp.event.CollectEvent;
import com.zjw.wanandroid_mvp.contract.project.ProjectListContract;
import com.zjw.wanandroid_mvp.event.LoginEvent;
import com.zjw.wanandroid_mvp.presenter.project.ProjectListPresenter;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.RecyclerUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.CollectView;
import com.zjw.wanandroid_mvp.widget.callback.EmptyCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.greenrobot.eventbus.Subscribe;
import org.jetbrains.annotations.NotNull;
import java.util.List;

import butterknife.BindView;

public class ProjectListFragment extends BaseFragment<ProjectListPresenter> implements ProjectListContract.IProjectListView {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;
    @BindView(R.id.floating_action_btn)
    FloatingActionButton floatingActionButton;

    private int id;

    private int initPage = 1;
    private int currentPage = initPage;

    private ArticleAdapter mAdapter;

    private LoadService loadService;


    public ProjectListFragment(int id) {
        this.id = id;
    }

    @Override
    public void setupFragmentComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerProjectListComponent.builder()
                .appComponent(appComponent)
                .projectListModule(new ProjectListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_project_list, container, false);
        loadService = LoadSir.getDefault().register(rootView.findViewById(R.id.swipeRefreshLayout), (Callback.OnReloadListener) v -> {
            loadService.showCallback(LoadingCallback.class);
            currentPage = initPage;
            mPresenter.getProjectList(currentPage, id);
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
                currentPage = initPage;
                mPresenter.getProjectList(currentPage, id);
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
                mPresenter.getProjectList(currentPage, id);
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(-1)) {
                    floatingActionButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    @Override
    public void showProjectList(BasePageBean<List<ArticleBean>> bean) {
        refreshLayout.setRefreshing(false);
        if (currentPage == initPage && bean.getDatas().size() == 0) {
            loadService.showCallback(EmptyCallback.class);
        } else if (currentPage == initPage) {
            loadService.showSuccess();
            mAdapter.setNewInstance(bean.getDatas());
        } else {
            loadService.showSuccess();
            mAdapter.addData(bean.getDatas());
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

    public void scrollToTop() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        if (layoutManager.findFirstVisibleItemPosition() >= 40) {
            recyclerView.scrollToPosition(0);
        } else {
            recyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadService.showCallback(LoadingCallback.class);
        recyclerView.setAdapter(mAdapter);
        mPresenter.getProjectList(currentPage, id);
    }

    @Override
    public void collect(boolean collect, int position) {
        new CollectEvent(collect, mAdapter.getItem(position).getId()).post();
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new ArticleAdapter();
        recyclerView.setAdapter(mAdapter);

        mAdapter.addChildClickViewIds(R.id.iv_collection);
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                ArticleBean bean = (ArticleBean) adapter.getItem(position);
                JumpWebUtils.startWebActivity(mContext, bean.getTitle(), bean.getLink());
            }
        });

        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe
    public void freshLogin(@NonNull LoginEvent event) {
        List<ArticleBean> data = mAdapter.getData();
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
        mAdapter.notifyDataSetChanged();
    }

    @Subscribe
    public void collectEvent(CollectEvent event) {
        List<ArticleBean> data = mAdapter.getData();
        if (!event.isCollect()) {

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId() == event.getId()) {
                    data.get(i).setCollect(false);
                    break;
                }
            }

            mAdapter.notifyDataSetChanged();
        }
    }

}
