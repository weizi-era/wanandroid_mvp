package com.zjw.wanandroid_mvp.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.bean.ScoreListBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class IntegralAdapter extends BaseQuickAdapter<ScoreListBean, BaseViewHolder> {

    public IntegralAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, ScoreListBean datas) {
        String desc = datas.getDesc();
        String time = desc.replace(datas.getReason() + " ,", "");
        viewHolder.setText(R.id.reason, datas.getReason())
                .setText(R.id.time, time)
                .setText(R.id.upScore, "+" + datas.getCoinCount());
    }
}
