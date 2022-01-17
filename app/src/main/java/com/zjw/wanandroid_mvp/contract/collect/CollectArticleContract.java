package com.zjw.wanandroid_mvp.contract.collect;

import com.jess.arms.mvp.IModel;

import com.zjw.wanandroid_mvp.base.IView;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;

import java.util.List;

import io.reactivex.Observable;

public interface CollectArticleContract {

    interface ICollectArticleView extends IView {
        void showArticleList(BasePageBean<List<ArticleBean>> bean);
        void unCollect(int position);
    }

    interface ICollectArticleModel extends IModel {
        Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getArticleList(int page);
        Observable<BaseBean<Object>> unCollect(int articleId, int originId);
    }
}
