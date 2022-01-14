package com.zjw.wanandroid_mvp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zjw.wanandroid_mvp.R;

import org.jetbrains.annotations.NotNull;

public class HistorySearchAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public HistorySearchAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, String s) {
        viewHolder.setText(R.id.tv_history, s)
                .setImageResource(R.id.iv_delete, R.mipmap.ic_delete);
    }
}
