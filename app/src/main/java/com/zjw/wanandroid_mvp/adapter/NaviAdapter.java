package com.zjw.wanandroid_mvp.adapter;

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
import com.zjw.wanandroid_mvp.bean.NaviBean;
import com.zjw.wanandroid_mvp.utils.JumpWebUtils;
import com.zjw.wanandroid_mvp.utils.Utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class NaviAdapter extends BaseQuickAdapter<NaviBean, BaseViewHolder> {

    private LayoutInflater inflater;

    public NaviAdapter(int layoutResId, LayoutInflater inflater) {
        super(layoutResId);
        this.inflater = inflater;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, NaviBean bean) {
        List<String> flowList = new ArrayList<>();
        List<String> urlList = new ArrayList<>();

        viewHolder.setText(R.id.flow_title, bean.getName());

        bean.getArticles().forEach(name -> flowList.add(name.getTitle()));
        bean.getArticles().forEach(name -> urlList.add(name.getLink()));

        TagFlowLayout tagFlowLayout = viewHolder.getView(R.id.flowlayout);
        tagFlowLayout.setAdapter(new TagAdapter<String>(flowList) {
            @Override
            public View getView(FlowLayout parent, int position, String title) {
                TextView itemTag = (TextView) inflater.inflate(R.layout.item_flow_system, tagFlowLayout, false);
                itemTag.setTextColor(Utils.randomColor());
                itemTag.setText(title);

                return itemTag;
            }
        });

        tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                JumpWebUtils.startWebActivity(parent.getContext(), flowList.get(position), urlList.get(position));
                return true;
            }
        });
    }
}
