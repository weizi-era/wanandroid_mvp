package com.zjw.wanandroid_mvp.adapter;

import android.util.TypedValue;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.cardview.widget.CardView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.zjw.wanandroid_mvp.R;
import com.zjw.wanandroid_mvp.bean.TodoBean;

import org.jetbrains.annotations.NotNull;

public class TodoAdapter extends BaseQuickAdapter<TodoBean, BaseViewHolder> {

    public TodoAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder viewHolder, TodoBean bean) {
        viewHolder.setText(R.id.todo_title, bean.getTitle())
                .setText(R.id.todo_date, bean.getDateStr())
                .setText(R.id.todo_content, bean.getContent());

        TypedValue typedValue = new TypedValue();

        if (bean.getStatus() == 1) {
            // 已完成状态
            viewHolder.setGone(R.id.iv_complete, false);
            ((CardView)viewHolder.getView(R.id.item_view)).setForeground(AppCompatResources.getDrawable(getContext(), R.drawable.forground_shap));
        } else {
            // 未完成状态
            viewHolder.setGone(R.id.iv_complete, true);
            getContext().getTheme().resolveAttribute(R.attr.selectableItemBackground, typedValue, true);
            ((CardView) viewHolder.getView(R.id.item_view)).setForeground(AppCompatResources.getDrawable(getContext(), typedValue.resourceId));
        }

        if (bean.getPriority() == 1) {
            viewHolder.setImageResource(R.id.todo_tag, R.drawable.import_tag);
        } else if (bean.getPriority() == 2) {
            viewHolder.setImageResource(R.id.todo_tag, R.drawable.general);
        } else if (bean.getPriority() == 3) {
            viewHolder.setImageResource(R.id.todo_tag, R.drawable.tips);
        }
    }
}
