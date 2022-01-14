package com.zjw.wanandroid_mvp.contract.system;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;

import java.util.List;

import io.reactivex.Observable;

public interface SystemArticleContract {

    interface ISystemArticleView extends IView {
        void showArticleList(BasePageBean<List<ArticleBean>> bean);
        void collect(boolean collect, int position);
    }

    interface ISystemArticleModel extends IModel {
        Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getArticleList(int page, int cid);
        Observable<BaseBean<Object>> collect(int articleId);
        Observable<BaseBean<Object>> unCollect(int articleId);
    }
}
