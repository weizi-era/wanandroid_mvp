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
import com.zjw.wanandroid_mvp.adapter.WebsiteAdapter;
import com.zjw.wanandroid_mvp.base.BaseFragment;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.bean.WebsiteBean;
import com.zjw.wanandroid_mvp.contract.collect.CollectWebsiteContract;
import com.zjw.wanandroid_mvp.di.component.collect.DaggerCollectWebsiteComponent;
import com.zjw.wanandroid_mvp.di.module.collect.CollectWebsiteModule;
import com.zjw.wanandroid_mvp.event.CollectEvent;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.collect.CollectArticleContract;
import com.zjw.wanandroid_mvp.presenter.collect.CollectArticlePresenter;
import com.zjw.wanandroid_mvp.presenter.collect.CollectWebsitePresenter;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.ToastUtil;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

public class CollectWebsiteFragment extends BaseFragment<CollectWebsitePresenter> implements CollectWebsiteContract.ICollectWebsiteView {

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.swipeRecyclerview)
    SwipeRecyclerView recyclerView;

    private LoadService loadService;
    private WebsiteAdapter websiteAdapter;

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
                Utils.setLoadingColor(loadService);
                loadService.showCallback(LoadingCallback.class);
                mPresenter.getWebsiteList();
            }
        });

        return rootView;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        initAdapter();
    }


    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        websiteAdapter = new WebsiteAdapter(R.layout.item_website);
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

    @Override
    public void showWebsiteList(List<WebsiteBean> bean) {
        websiteAdapter.setList(bean);
    }

    @Override
    public void unCollect(int position) {
        new CollectEvent(false, websiteAdapter.getItem(position).getId()).post();
    }
}