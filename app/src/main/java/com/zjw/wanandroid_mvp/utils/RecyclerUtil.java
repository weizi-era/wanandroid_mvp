package com.zjw.wanandroid_mvp.utils;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.yanzhenjie.recyclerview.SwipeRecyclerView;
import com.zjw.wanandroid_mvp.widget.DefineLoadMoreView;

public class RecyclerUtil {

    public static DefineLoadMoreView initRecyclerView(Context context, SwipeRecyclerView recyclerView, SwipeRecyclerView.LoadMoreListener loadMoreListener) {
        DefineLoadMoreView footerView = new DefineLoadMoreView(context);
        recyclerView.addFooterView(footerView);
        recyclerView.setLoadMoreView(footerView);//添加加载更多尾部
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setHasFixedSize(true);
        recyclerView.setLoadMoreListener(loadMoreListener);//设置加载更多回调
        footerView.setLoadMoreListener(new SwipeRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                footerView.onLoading();
                loadMoreListener.onLoadMore();
            }
        });

        return footerView;
    }
}
