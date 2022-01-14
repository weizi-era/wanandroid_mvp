package com.zjw.wanandroid_mvp.contract.share;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.SharedBean;

import io.reactivex.Observable;

public interface OtherShareContract {

    interface IOtherShareView extends IView {
        void showArticleList(SharedBean bean);
        void collect(boolean collect, int position);
        void unCollect(boolean collect, int position);
    }

    interface IOtherShareModel extends IModel {
        Observable<BaseBean<SharedBean>> getArticleList(int page, int id);
        Observable<BaseBean<Object>> collect(int articleId);
        Observable<BaseBean<Object>> unCollect(int articleId);
    }
}
