package com.zjw.wanandroid_mvp.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.bean.TodoListBean;

import org.jetbrains.annotations.NotNull;

public class TodoAdapter extends BaseQuickAdapter<TodoListBean, BaseViewHolder> {

    public TodoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, TodoListBean bean) {
        viewHolder.setText(R.id.todo_title, bean.getTitle())
                .setText(R.id.todo_date, bean.getDateStr())
                .setText(R.id.todo_content, bean.getContent())
                .setImageResource(R.id.todo_tag, R.drawable.import_tag);
    }
}
