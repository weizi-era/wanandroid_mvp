package com.zjw.wanandroid_mvp.adapter;

import android.text.Html;
import android.text.TextUtils;

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

public class ArticleAdapter extends BaseDelegateMultiAdapter<ArticleBean, BaseViewHolder> {

    public ArticleAdapter() {
        super();
        setMultiTypeDelegate(new BaseMultiTypeDelegate<ArticleBean>() {
            @Override
            public int getItemType(@NotNull List<? extends ArticleBean> list, int position) {
                ArticleBean articleBean = list.get(position);
                return TextUtils.isEmpty(articleBean.getEnvelopePic())? Constant.TYPE_ARTICLE : Constant.TYPE_PROJECT;
            }
        });

        getMultiTypeDelegate().addItemType(Constant.TYPE_ARTICLE, R.layout.item_home)
                .addItemType(Constant.TYPE_PROJECT, R.layout.item_project);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, ArticleBean bean) {

        switch (viewHolder.getItemViewType()) {
            case Constant.TYPE_ARTICLE:
                viewHolder.setText(R.id.author_name, TextUtils.isEmpty(bean.getAuthor()) ? bean.getShareUser() : bean.getAuthor())
                        .setText(R.id.article_title, Html.fromHtml(bean.getTitle()))
                        .setText(R.id.time, bean.getNiceDate())
                        .setText(R.id.super_classify, bean.getSuperChapterName())
                        .setText(R.id.classify, bean.getChapterName())
                        // 这个地方有个巨坑  2.x升级到3.x后  setGone的行为判断都反了，false是显示，true是隐藏，我是懒得降版本了  要注意。
                        .setGone(R.id.tv_new, !bean.isFresh())
                        .setGone(R.id.top, bean.getType() == 0)
                        .setImageResource(R.id.iv_collection, bean.isCollect() ? R.mipmap.star_collected : R.mipmap.star_default);

                    if (bean.getTags() != null && !bean.getTags().isEmpty()) {
                        viewHolder.setGone(R.id.tags, false);
                        viewHolder.setText(R.id.tags, bean.getTags().get(0).getName());
                        if (bean.getTags().size() == 1) {
                            viewHolder.setGone(R.id.tags1, true);
                        } else {
                            viewHolder.setGone(R.id.tags1, false);
                            viewHolder.setText(R.id.tags1, bean.getTags().get(1).getName());
                        }
                    } else {
                        viewHolder.setGone(R.id.tags, true);
                        viewHolder.setGone(R.id.tags1, true);
                    }
                break;
            case Constant.TYPE_PROJECT:
                viewHolder.setText(R.id.author_name, TextUtils.isEmpty(bean.getAuthor()) ? bean.getShareUser() : bean.getAuthor())
                        .setText(R.id.article_title, Html.fromHtml(bean.getTitle()))
                        .setText(R.id.time, bean.getNiceDate())
                        .setText(R.id.article_desc, Html.fromHtml(bean.getDesc()))
                        .setText(R.id.super_classify, bean.getSuperChapterName())
                        .setText(R.id.classify, bean.getChapterName())
                        .setImageResource(R.id.iv_collection, bean.isCollect() ? R.mipmap.star_collected : R.mipmap.star_default);

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
