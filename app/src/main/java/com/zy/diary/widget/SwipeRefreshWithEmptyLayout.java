package com.zy.diary.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

import com.zy.diary.R;

/**
 * Created by zhangyang on 2016/8/9.
 * 可以添加空布局的下拉刷新控件
 */
public class SwipeRefreshWithEmptyLayout extends SwipeRefreshLayout {

    private static final String TAG = SwipeRefreshWithEmptyLayout.class.getCanonicalName();
    private int mScrollableChildId;//控件ID
    private View mScrollableChild;//子控件

    public SwipeRefreshWithEmptyLayout(Context context) {
        this(context, null);
    }

    public SwipeRefreshWithEmptyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SwipeRefreshWithEmptyLayout);
        mScrollableChildId = a.getResourceId(R.styleable.SwipeRefreshWithEmptyLayout_scrollableChildId, 0);
        mScrollableChild = findViewById(mScrollableChildId);
        a.recycle();
    }

    @Override
    public boolean canChildScrollUp() {
        //判断有没有传入子控件
        ensureScrollableChild();
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mScrollableChild instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mScrollableChild;
                return absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop());
            } else {
                return mScrollableChild.getScrollY() > 0;
            }
        } else {
            return ViewCompat.canScrollVertically(mScrollableChild, -1);
        }
    }

    private void ensureScrollableChild() {
        if (mScrollableChild == null) {
            mScrollableChild = findViewById(mScrollableChildId);
        }
    }


}
