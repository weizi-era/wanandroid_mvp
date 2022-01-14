package com.zjw.wanandroid_mvp.contract.square;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;

import io.reactivex.Observable;

public interface PublishContract {

    interface IPublishView extends IView {
        void addArticle();
    }

    interface IPublishModel extends IModel {
        Observable<BaseBean<Object>> addArticle(String title, String link);
    }
}
