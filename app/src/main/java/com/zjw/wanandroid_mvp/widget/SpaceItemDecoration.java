package com.zjw.wanandroid_mvp.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private Drawable mDivider;
    private int mSectionOffsetV;
    private int mSectionOffsetH;
    private boolean mDrawOver;
    private int[] attrs;

    public SpaceItemDecoration(Context context) {
        this.mDrawOver = true;
        mSectionOffsetH = 0;
        mSectionOffsetV = 0;
        this.attrs = new int[] {android.R.attr.listDivider};
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        if (this.mDivider != null && this.mDrawOver) {
            draw(c, parent);
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (mDivider != null && mDrawOver) {
            draw(c, parent);
        }
    }

    @Override
    public void getItemOffsets(@NonNull  Rect outRect, @NonNull View view, @NonNull  RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (getOrientation(parent.getLayoutManager()) == RecyclerView.VERTICAL) {
            outRect.set(mSectionOffsetH, 0, mSectionOffsetH, mSectionOffsetV);
        } else {
            outRect.set(0, 0, mSectionOffsetV, 0);
        }
    }

    private void draw(Canvas c, RecyclerView parent) {
        int left = parent.getPaddingLeft();
        int right = parent.getWidth() - parent.getPaddingRight();
        int childCount = parent.getChildCount();
        int i;

        for (i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

            int bottom;
            int top = child.getBottom() + params.bottomMargin + Math.round(ViewCompat.getTranslationY(child));
            if (mDivider.getIntrinsicHeight() <= 0) {
                bottom = top + 1;
            } else {
                bottom = top + mDivider.getIntrinsicHeight();
            }

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private int getOrientation(RecyclerView.LayoutManager layoutManager) {
        if (layoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        }
        return androidx.recyclerview.widget.OrientationHelper.HORIZONTAL;
    }
}
