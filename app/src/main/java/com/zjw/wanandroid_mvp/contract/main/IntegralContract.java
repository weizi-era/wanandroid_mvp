package com.zjw.wanandroid_mvp.contract.main;

import com.jess.arms.mvp.IModel;
import com.jess.arms.mvp.IView;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.bean.ScoreListBean;

import java.util.List;

import io.reactivex.Observable;

public interface IntegralContract {

    interface IIntegralView extends IView {
        void showScoreList(BasePageBean<List<ScoreListBean>> bean);
    }

    interface IIntegralModel extends IModel {
        Observable<BaseBean<BasePageBean<List<ScoreListBean>>>> getScoreList(int page);
    }
}
