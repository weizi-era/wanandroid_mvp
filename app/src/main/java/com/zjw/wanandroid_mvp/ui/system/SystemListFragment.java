package com.zjw.wanandroid_mvp.ui.system;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.jess.arms.di.component.AppComponent;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.callback.SuccessCallback;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.SystemListAdapter;
import com.zjw.wanandroid_mvp.base.BaseFragment;
import com.zjw.wanandroid_mvp.bean.SystemBean;
import com.zjw.wanandroid_mvp.bean.TreeBean;
import com.zjw.wanandroid_mvp.di.component.system.DaggerSystemComponent;
import com.zjw.wanandroid_mvp.di.module.system.SystemModule;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.system.SystemContract;
import com.zjw.wanandroid_mvp.presenter.system.SystemPresenter;
import com.zjw.wanandroid_mvp.utils.Utils;
import com.zjw.wanandroid_mvp.widget.callback.ErrorCallback;
import com.zjw.wanandroid_mvp.widget.callback.LoadingCallback;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import butterknife.BindView;

public class SystemListFragment extends BaseFragment<SystemPresenter> implements SystemContract.ISystemView {

    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    private LoadService loadService;

    private SystemListAdapter adapter;

    @Override
    public void setupFragmentComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerSystemComponent.builder()
                .appComponent(appComponent)
                .systemModule(new SystemModule(this))
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_system_list, container, false);
        loadService = LoadSir.getDefault().register(rootView.findViewById(R.id.recyclerview), (Callback.OnReloadListener) v -> {
            loadService.showCallback(LoadingCallback.class);
            mPresenter.getSystemList();
        });

        Utils.setLoadingColor(loadService);
        return rootView;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        loadService.showCallback(LoadingCallback.class);
        recyclerView.setAdapter(adapter);
        mPresenter.getSystemList();
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        initAdapter();
    }

    @Override
    public void showSystemList(List<SystemBean> bean) {
        if (bean.size() == 0) {
            loadService.showCallback(ErrorCallback.class);
        } else {
            loadService.showCallback(SuccessCallback.class);
            adapter.setList(bean);
        }
    }

    private void initAdapter() {
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        adapter = new SystemListAdapter(R.layout.item_system, getLayoutInflater());
        recyclerView.setAdapter(adapter);
    }
}
