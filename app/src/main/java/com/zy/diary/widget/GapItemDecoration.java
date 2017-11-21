package com.zy.diary.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.zy.diary.utils.DensityUtil;

/**
 * Created by zhangyang on 2017/11/21.
 * item之间有间隔
 */
public class GapItemDecoration extends RecyclerView.ItemDecoration {

    public static final int VERTICAL = 1;
    public static final int HORIZONTAL = 2;

    private int mOrientation = VERTICAL;

    private int mGapWidth ;

    public GapItemDecoration(Context context) {
        this(context,VERTICAL,DensityUtil.dp2px(context,10));
    }

    public GapItemDecoration(Context context,int mOrientation) {
        this(context,mOrientation,DensityUtil.dp2px(context,10));
    }

    public GapItemDecoration(Context context,int mOrientation, int mGapWidth) {
        this.mOrientation = mOrientation;
        this.mGapWidth = mGapWidth;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        if (mOrientation == VERTICAL) {
            outRect.set(0, 0, 0, mGapWidth);
        } else {
            outRect.set(0, 0, mGapWidth, 0);
        }
    }
}
