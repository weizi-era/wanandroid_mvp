package com.zjw.wanandroid_mvp.adapter;

import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseDelegateMultiAdapter;
import com.chad.library.adapter.base.delegate.BaseMultiTypeDelegate;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.model.constant.Constant;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CollectArticleAdapter extends BaseDelegateMultiAdapter<ArticleBean, BaseViewHolder> {

    public CollectArticleAdapter() {
        super();
        setMultiTypeDelegate(new BaseMultiTypeDelegate<ArticleBean>() {
            @Override
            public int getItemType(@NotNull List<? extends ArticleBean> list, int position) {
                ArticleBean articleBean = list.get(position);
                return TextUtils.isEmpty(articleBean.getEnvelopePic())? Constant.TYPE_ARTICLE : Constant.TYPE_PROJECT;
            }
        });

        getMultiTypeDelegate().addItemType(Constant.TYPE_ARTICLE, R.layout.item_collect_article)
                .addItemType(Constant.TYPE_PROJECT, R.layout.item_collect_project);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, ArticleBean bean) {

        switch (viewHolder.getItemViewType()) {
            case Constant.TYPE_ARTICLE:
                viewHolder.setText(R.id.author_name, TextUtils.isEmpty(bean.getAuthor()) ? "匿名用户" : bean.getAuthor())
                        .setText(R.id.article_title, Html.fromHtml(bean.getTitle()))
                        .setText(R.id.time, bean.getNiceDate())
                        .setText(R.id.classify, bean.getChapterName())
                        .setImageResource(R.id.iv_collection, R.mipmap.star_collected);
                break;
            case Constant.TYPE_PROJECT:
                viewHolder.setText(R.id.author_name, TextUtils.isEmpty(bean.getAuthor()) ? "匿名用户" : bean.getAuthor())
                        .setText(R.id.article_title, Html.fromHtml(bean.getTitle()))
                        .setText(R.id.time, bean.getNiceDate())
                        .setText(R.id.article_desc, Html.fromHtml(bean.getDesc()))
                        .setText(R.id.classify, bean.getChapterName())
                        .setImageResource(R.id.iv_collection, R.mipmap.star_collected);

                ArmsUtils.obtainAppComponentFromContext(getContext()).imageLoader().loadImage(getContext().getApplicationContext(),
                        ImageConfigImpl
                                .builder()
                                .url(bean.getEnvelopePic())
                                .imageView(viewHolder.getView(R.id.project_pic))
                                .errorPic(R.drawable.default_project_img)
                                .fallback(R.drawable.default_project_img)
                                .isCrossFade(true)
                                .build()
                );
                break;
        }
    }
}
