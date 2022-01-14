package com.zjw.wanandroid_mvp.model.main;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;
import com.zjw.wanandroid_mvp.bean.ArticleBean;
import com.zjw.wanandroid_mvp.bean.BaseBean;
import com.zjw.wanandroid_mvp.bean.BasePageBean;
import com.zjw.wanandroid_mvp.model.api.ApiServer;
import com.zjw.wanandroid_mvp.contract.main.QuestionContract;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@ActivityScope
public class QuestionModel extends BaseModel implements QuestionContract.IQuestionModel {


    @Inject
    public QuestionModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public Observable<BaseBean<BasePageBean<List<ArticleBean>>>> getQuestionList(int page) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).getQuestionList(page);
    }

    @Override
    public Observable<BaseBean<Object>> collect(int articleId) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).collectArticle(articleId);
    }

    @Override
    public Observable<BaseBean<Object>> unCollect(int articleId) {
        return mRepositoryManager.obtainRetrofitService(ApiServer.class).unCollectArticle(articleId);
    }
}
