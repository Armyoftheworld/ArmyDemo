package com.example.scorllbarpanel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/9/1
 * @description
 */
public class SweepPanelListView extends ListView implements AbsListView.OnScrollListener {
    private View mScrollBarPanel;
    private int mWidthMessureSpec;
    private int mHeightMeasureSpec;
    private int mScrollBarPanelPosition = 0;
    private Animation mInAnimation, mOutAnimation;
    private int thumbOffset;
    private int mLastPositon = 0;

    public SweepPanelListView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SweepPanelListView);
        int layoutId = a.getResourceId(R.styleable.SweepPanelListView_scrollBarPanel, R.layout.scrollbarpanel);
        int inAnimId = a.getResourceId(R.styleable.SweepPanelListView_scrollBarPanelInAnimation, R.anim.in);
        int outAnimId = a.getResourceId(R.styleable.SweepPanelListView_scrollBarPanelOutAnimation, R.anim.out);
        a.recycle();

        setOnScrollListener(this);
        mInAnimation = AnimationUtils.loadAnimation(context, inAnimId);
        mOutAnimation = AnimationUtils.loadAnimation(context, outAnimId);
        mOutAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (mScrollBarPanel != null) {
                    mScrollBarPanel.setVisibility(GONE);
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        mScrollBarPanel = LayoutInflater.from(context).inflate(layoutId, null);
        mScrollBarPanel.setVisibility(GONE);
        requestLayout();

    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //1、监听当前指示器在哪个位置
        if (mScrollBarPanel != null && onPositionChangedListener != null) {
            /**
             * computeVerticalScrollRange();0~10000,纵向滚动条代表的整个纵向范围
             computeVerticalScrollExtent();滚动条在纵向范围内他自身的厚度的幅度
             computeVerticalScrollOffset();滚动条的纵向幅度的位置
             */
            //滑块的厚度   滑块的厚度/ListView的高度 = extend/Range
            int height = Math.round(getMeasuredHeight() * computeHorizontalScrollExtent() / computeVerticalScrollRange());

            //滑块正中间的Y坐标
            //滑块的高度/extent = thumbOffset/offset
            thumbOffset = height * computeVerticalScrollOffset() / computeVerticalScrollExtent();
            thumbOffset += height / 2;

            int left = getMeasuredWidth() - mScrollBarPanel.getMeasuredWidth() - getVerticalScrollbarWidth();
            mScrollBarPanelPosition = thumbOffset - mScrollBarPanel.getHeight() / 2;
            mScrollBarPanel.layout(left, mScrollBarPanelPosition, left + mScrollBarPanel.getMeasuredWidth(), mScrollBarPanelPosition + mScrollBarPanel.getMeasuredHeight());

            for (int i = 0; i < getChildCount(); i++) {
                View childView = getChildAt(i);
                if (childView != null) {
                    //判断滑块是否在当前条目的范围内
                    if (thumbOffset > childView.getTop() && thumbOffset < childView.getBottom()) {
                        if (mLastPositon != firstVisibleItem + 1) {
                            mLastPositon = firstVisibleItem + i;

                            onPositionChangedListener.onPositionChanged(this, mLastPositon, mScrollBarPanel);

                            //因为text宽度会改变，需要重新测量
                            if (isDraw) {
                                measureChild(mScrollBarPanel, mWidthMessureSpec, mHeightMeasureSpec);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected boolean awakenScrollBars(int startDelay, boolean invalidate) {
        //当滑动的时候滑动控件会唤醒
        boolean isAnimationPlayed = super.awakenScrollBars(startDelay, invalidate);
        if (isAnimationPlayed && mScrollBarPanel != null) {
            if (mScrollBarPanel.getVisibility() == GONE && mInAnimation != null) {
                mScrollBarPanel.setVisibility(VISIBLE);
                mScrollBarPanel.startAnimation(mInAnimation);
            }
            //过一段时间消失
            handler.removeCallbacks(mScrollBarPanelOutRunnable);
            handler.postAtTime(mScrollBarPanelOutRunnable, startDelay + AnimationUtils.currentAnimationTimeMillis());
        }
        return isAnimationPlayed;
    }

    private Runnable mScrollBarPanelOutRunnable = new Runnable() {
        @Override
        public void run() {
            if (mOutAnimation != null) {
                mScrollBarPanel.startAnimation(mOutAnimation);
            }
        }
    };
    private Handler handler = new Handler();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //计算测量尺寸（自己listview，panel的尺寸也计算好，后续还需要吧panel摆放到适合的位置）
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //计算panel的尺寸
        if (mScrollBarPanel != null && getAdapter() != null && isDraw) {
            mWidthMessureSpec = widthMeasureSpec;
            mHeightMeasureSpec = heightMeasureSpec;
            measureChild(mScrollBarPanel, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (mScrollBarPanel != null) {
            int left = getMeasuredWidth() - mScrollBarPanel.getMeasuredWidth() - getVerticalScrollbarWidth();
            int top = mScrollBarPanelPosition;
            int right = left + mScrollBarPanel.getMeasuredWidth();
            int bottom = top + mScrollBarPanel.getMeasuredHeight();
            mScrollBarPanel.layout(left, top, right, bottom);
        }
    }

    private boolean isDraw = false;

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //在listview绘制的时候在上面加一个自己绘制的部分盖在上面
        if (mScrollBarPanel != null && mScrollBarPanel.getVisibility() == VISIBLE) {
            drawChild(canvas, mScrollBarPanel, getDrawingTime());
            isDraw = true;
        }
    }

    public static interface onPositionChangedListener {
        void onPositionChanged(SweepPanelListView listView, int position, View scrollBarPanel);
    }

    private onPositionChangedListener onPositionChangedListener;

    public void setOnPositionChangedListener(SweepPanelListView.onPositionChangedListener onPositionChangedListener) {
        this.onPositionChangedListener = onPositionChangedListener;
    }
}
