package com.zjw.wanandroid_mvp.contract.collect;

import com.jess.arms.mvp.IModel;
import com.zjw.wanandroid_mvp.base.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.WebsiteBean;

import java.util.List;

import io.reactivex.Observable;

public interface CollectWebsiteContract {

    interface ICollectWebsiteView extends IView {
        void showWebsiteList(List<WebsiteBean> bean);
        void unCollect(int position);
    }

    interface ICollectWebsiteModel extends IModel {
        Observable<BaseBean<List<WebsiteBean>>> getWebsiteList();
        Observable<BaseBean<Object>> unCollect(int articleId);
    }
}
