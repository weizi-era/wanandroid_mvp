package com.zjw.wanandroid_mvp.adapter;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.SharedBean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ShareCommonAdapter extends BaseQuickAdapter<ArticleBean, BaseViewHolder> {

    public ShareCommonAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, ArticleBean bean) {
        viewHolder.setText(R.id.author_name, TextUtils.isEmpty(bean.getAuthor()) ? bean.getShareUser() : bean.getAuthor())
                .setText(R.id.article_title, bean.getTitle())
                .setText(R.id.time, bean.getNiceDate())
                .setText(R.id.super_classify, bean.getSuperChapterName())
                .setText(R.id.classify, bean.getChapterName())
                .setImageResource(R.id.iv_collection, bean.isCollect() ? R.mipmap.star_collected : R.mipmap.star_default)
                .setGone(R.id.tv_audit, bean.getAudit() != 0)
                .setGone(R.id.tv_new, !bean.isFresh());
    }

}
