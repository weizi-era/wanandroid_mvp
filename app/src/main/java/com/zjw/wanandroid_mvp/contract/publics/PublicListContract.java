package com.zjw.wanandroid_mvp.contract.publics;

import com.jess.arms.mvp.IModel;
import com.zjw.wanandroid_mvp.base.IView;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;

import java.util.List;

import io.reactivex.Observable;

public interface PublicListContract {

    interface IPublicListView extends IView {
        void showPublicList(BasePageBean<List<ArticleBean>> bean);
        void collect(boolean collect, int position);
    }

    interface IPublicListModel extends IModel {
        Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getPublicList(int id, int page);
        Observable<BaseBean<Object>> collect(int articleId);
        Observable<BaseBean<Object>> unCollect(int articleId);
    }
}
