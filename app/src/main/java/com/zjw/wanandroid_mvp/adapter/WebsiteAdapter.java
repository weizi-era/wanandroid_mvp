package com.zjw.wanandroid_mvp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.bean.ScoreListBean;
import com.zjw.wanandroid_mvp.bean.WebsiteBean;

import org.jetbrains.annotations.NotNull;

public class WebsiteAdapter extends BaseQuickAdapter<WebsiteBean, BaseViewHolder> {

    public WebsiteAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, WebsiteBean data) {
        viewHolder.setText(R.id.title, data.getName())
                .setText(R.id.link, data.getLink())
                .setImageResource(R.id.iv_collection, R.mipmap.star_collected);
    }
}
