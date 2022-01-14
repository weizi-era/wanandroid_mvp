package com.zjw.wanandroid_mvp.contract.home.search;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;

import java.util.List;

import io.reactivex.Observable;

public interface SearchResultContract {

    interface ISearchResultView extends IView {
        void showSearchResultList(BasePageBean<List<ArticleBean>> bean);
        void collect(boolean collect, int position);
    }

    interface ISearchResultModel extends IModel {
        Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getSearchResultList(int page, String key);
        Observable<BaseBean<Object>> collect(int articleId);
        Observable<BaseBean<Object>> unCollect(int articleId);
    }

}
