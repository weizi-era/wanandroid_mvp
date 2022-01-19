package com.zjw.wanandroid_mvp.contract.home;


import com.jess.arms.mvp.IModel;
import com.zjw.wanandroid_mvp.base.IView;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BannerBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;

import java.util.List;

import io.reactivex.Observable;


public interface HomeContract {

    interface IHomeView extends IView {
        void showBanner(List<BannerBean> beanList);
        void showHomeArticle(BasePageBean<List<ArticleBean>> beanList);
        void collect(boolean collect, int position);
    }

    interface IHomeModel extends IModel {
        Observable<BaseBean<List<BannerBean>>> loadBanner();
        Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getHomeArticle(int page);
        Observable<BaseBean<List<ArticleBean>>> getTopArticle();
        Observable<BaseBean<Object>> collect(int articleId);
        Observable<BaseBean<Object>> unCollect(int articleId);
    }
}
