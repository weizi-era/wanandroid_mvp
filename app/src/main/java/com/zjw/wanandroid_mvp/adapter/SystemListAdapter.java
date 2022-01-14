package com.zjw.wanandroid_mvp.adapter;

import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.bean.SystemBean;
import com.zjw.wanandroid_mvp.bean.TreeBean;
import com.zjw.wanandroid_mvp.ui.system.SystemArticleActivity;
import com.zjw.wanandroid_mvp.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SystemListAdapter extends BaseQuickAdapter<SystemBean, BaseViewHolder> {

    private LayoutInflater inflater;
    public SystemListAdapter(int layoutResId, LayoutInflater inflater) {
        super(layoutResId);
        this.inflater = inflater;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, SystemBean datas) {
        viewHolder.setText(R.id.tag, datas.getName());
        List<String> flowList = new ArrayList<>();
        List<Integer> cidList = new ArrayList<>();

        List<TreeBean> children = datas.getChildren();
        children.forEach(data -> flowList.add(data.getName()));
        children.forEach(data -> cidList.add(data.getId()));

        TagFlowLayout tagFlowLayout = viewHolder.getView(R.id.flowlayout);
        tagFlowLayout.setAdapter(new TagAdapter<String>(flowList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView itemTag = (TextView) inflater.inflate(R.layout.item_flow_system, tagFlowLayout, false);
                itemTag.setTextColor(Utils.randomColor());
                itemTag.setText(s);
                return itemTag;
            }
        });

        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                Intent intent = new Intent(parent.getContext(), SystemArticleActivity.class);
                intent.putExtra("title", flowList.get(position));
                intent.putExtra("cid", cidList.get(position));
                parent.getContext().startActivity(intent);
                return true;
            }
        });
    }
}
