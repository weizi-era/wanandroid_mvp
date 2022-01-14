package com.zjw.wanandroid_mvp.model.collect;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.jess.arms.di.scope.FragmentScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.contract.collect.CollectArticleContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@FragmentScope
public class CollectArticleModel extends BaseModel implements CollectArticleContract.ICollectArticleModel {

    @Inject
    public CollectArticleModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getArticleList(int page) {

        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getArticleList(page);
    }

    @Override
    public Observable<BaseBean<Object>> unCollect(int articleId, int originId) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).unMyCollect(articleId, originId);
    }

}
