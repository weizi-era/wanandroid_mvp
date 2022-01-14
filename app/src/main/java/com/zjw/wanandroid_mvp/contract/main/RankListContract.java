package com.zjw.wanandroid_mvp.contract.main;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.bean.RankListBean;

import java.util.List;

import io.reactivex.Observable;

public interface RankListContract {

    interface IRankListView extends IView {
        void showRankList(BasePageBean<List<RankListBean>> bean);
    }

    interface IRankListModel extends IModel {
        Observable<BaseBean<BasePageBean<List<RankListBean>>>> getRankList(int page);
    }
}
