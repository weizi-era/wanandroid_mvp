package com.zjw.wanandroid_mvp.contract.share;

import com.jess.arms.mvp.IModel;
import com.zjw.wanandroid_mvp.base.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.SharedBean;

import io.reactivex.Observable;

public interface MyShareContract {

    interface IMyShareView extends IView {
        void showArticleList(SharedBean bean);
        void deleteArticle(int position);
    }

    interface IMyShareModel extends IModel {
        Observable<BaseBean<SharedBean>> getArticleList(int page);
        Observable<BaseBean<Object>> deleteArticle(int articleId);
    }
}
