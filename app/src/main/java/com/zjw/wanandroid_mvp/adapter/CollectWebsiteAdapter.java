package com.zjw.wanandroid_mvp.adapter;

import android.text.Html;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseDelegateMultiAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.WebsiteBean;
import com.zjw.wanandroid_mvp.model.constant.Constant;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CollectWebsiteAdapter extends BaseQuickAdapter<WebsiteBean, BaseViewHolder> {

    public CollectWebsiteAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, WebsiteBean bean) {
        viewHolder.setText(R.id.tv_title, bean.getName())
                .setText(R.id.tv_link, bean.getLink())
                .setImageResource(R.id.iv_collection, R.mipmap.star_collected);
    }
}
