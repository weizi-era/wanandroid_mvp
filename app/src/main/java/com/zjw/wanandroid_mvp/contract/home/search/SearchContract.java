package com.zjw.wanandroid_mvp.contract.home.search;

import com.jess.arms.mvp.IModel;
import com.zjw.wanandroid_mvp.base.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.HotSearchBean;

import java.util.List;

import io.reactivex.Observable;

public interface SearchContract {

    interface ISearchView extends IView {
        void showHotSearch(List<HotSearchBean> bean);
    }

    interface ISearchModel extends IModel {
        Observable<BaseBean<List<HotSearchBean>>> getHotSearch();
    }
}
