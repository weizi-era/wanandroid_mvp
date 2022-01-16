package com.zjw.wanandroid_mvp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.bean.RankListBean;


import org.jetbrains.annotations.NotNull;

public class RankListAdapter extends BaseQuickAdapter<RankListBean, BaseViewHolder> {

    public RankListAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, RankListBean bean) {

        viewHolder.setText(R.id.username, bean.getUsername())
                .setText(R.id.rank, bean.getRank())
                .setText(R.id.rank_number, String.valueOf(bean.getCoinCount()));
    }
}
