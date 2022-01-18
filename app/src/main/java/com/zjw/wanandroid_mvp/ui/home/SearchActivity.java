package com.zjw.wanandroid_mvp.ui.home;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.google.gson.Gson;
import com.jess.arms.di.component.AppComponent;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.adapter.HistorySearchAdapter;
import com.zjw.wanandroid_mvp.base.BaseActivity;
import com.zjw.wanandroid_mvp.bean.HotSearchBean;
import com.zjw.wanandroid_mvp.di.component.home.search.DaggerSearchComponent;
import com.zjw.wanandroid_mvp.di.module.home.search.SearchModule;
import com.zjw.wanandroid_mvp.model.constant.Constant;
import com.zjw.wanandroid_mvp.contract.home.search.SearchContract;
import com.zjw.wanandroid_mvp.presenter.home.search.HotSearchPresenter;
import com.zjw.wanandroid_mvp.utils.CacheUtil;
import com.zjw.wanandroid_mvp.utils.DialogUtil;
import com.zjw.wanandroid_mvp.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;

public class SearchActivity extends BaseActivity<HotSearchPresenter> implements SearchContract.ISearchView {

    @BindView(R.id.hot_search_flow_layout)
    TagFlowLayout tagFlowLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rv_history_search)
    RecyclerView recyclerView;
    @BindView(R.id.search_history_clear_all_tv)
    TextView clearAll;
    @BindView(R.id.empty_history)
    TextView empty_history;

    private HistorySearchAdapter mHistorySearchAdapter;

    private List<String> historyList;
    private List<HotSearchBean> hotList = new ArrayList<>();


    @Override
    public void setupActivityComponent(@NonNull @NotNull AppComponent appComponent) {
        DaggerSearchComponent.builder()
                .appComponent(appComponent)
                .searchModule(new SearchModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_search;
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);

        initAdapter();

        historyList = CacheUtil.getHistorySearchCache();
        if (historyList.size() != 0) {
            mHistorySearchAdapter.setList(historyList);
        } else {
            empty_history.setVisibility(View.VISIBLE);
        }

        clearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showConfirmDialog(SearchActivity.this, "确定清空搜索历史吗？", () -> {
                    historyList.clear();
                    CacheUtil.setHistorySearchCache(new Gson().toJson(historyList));
                    empty_history.setVisibility(View.VISIBLE);
                    mHistorySearchAdapter.setNewInstance(historyList);
                });
            }
        });

        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                HotSearchBean data = hotList.get(position);
                if (!historyList.contains(data.getName())) {
                    historyList.add(0, data.getName());
                } else {
                    Collections.swap(historyList, historyList.indexOf(data.getName()), 0);
                }

                CacheUtil.setHistorySearchCache(new Gson().toJson(historyList));

                if (historyList.size() > 0) {
                    empty_history.setVisibility(View.GONE);
                }

                mHistorySearchAdapter.setList(historyList);

                Intent intent = new Intent(parent.getContext(), SearchResultActivity.class);
                intent.putExtra("key", data.getName());
                parent.getContext().startActivity(intent);
                return true;
            }
        });

        mPresenter.getHotSearch();
    }


    private void initAdapter() {
        mHistorySearchAdapter = new HistorySearchAdapter(R.layout.item_history_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mHistorySearchAdapter);
        mHistorySearchAdapter.setList(historyList);

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);

        mHistorySearchAdapter.addChildClickViewIds(R.id.iv_delete);
        mHistorySearchAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull @NotNull BaseQuickAdapter adapter, @NonNull @NotNull View view, int position) {
                String item = (String) adapter.getItem(position);
                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);

                intent.putExtra("key", item);
                startActivity(intent);
            }
        });

        mHistorySearchAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull @NotNull BaseQuickAdapter<?, ?> adapter, @NonNull @NotNull View view, int position) {
                String item = (String) adapter.getItem(position);
                historyList.remove(item);
                CacheUtil.setHistorySearchCache(new Gson().toJson(historyList));
                if (historyList.size() == 0) {
                    empty_history.setVisibility(View.VISIBLE);
                }
                mHistorySearchAdapter.remove(item);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_searchlist, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.onActionViewExpanded();
        searchView.setQueryHint("输入关键字搜索");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (!historyList.contains(query)) {
                    historyList.add(0, query);
                }

                CacheUtil.setHistorySearchCache(new Gson().toJson(historyList));

                if (historyList.size() > 0) {
                    empty_history.setVisibility(View.GONE);
                }

                mHistorySearchAdapter.setList(historyList);

                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("key", query);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setSubmitButtonEnabled(true);

        try {
            Class<?> clazz = Class.forName("androidx.appcompat.widget.SearchView");
            Field mGoButton = clazz.getDeclaredField("mGoButton");
            mGoButton.setAccessible(true);
            ImageView imageView = (ImageView) mGoButton.get(searchView);
            imageView.setImageResource(R.mipmap.icon_search);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean useEventBus() {
        return false;
    }

    @Override
    public void showHotSearch(List<HotSearchBean> bean) {
        hotList.addAll(bean);
        tagFlowLayout.setAdapter(new TagAdapter<HotSearchBean>(hotList) {
            @Override
            public View getView(FlowLayout parent, int position, HotSearchBean data) {
                TextView itemTag = (TextView) getLayoutInflater().inflate(R.layout.item_flow_system, tagFlowLayout, false);
                itemTag.setTextColor(Utils.randomColor());
                itemTag.setText(data.getName());
                return itemTag;
            }
        });
    }
}