package com.army.parallaxscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ListView;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/10/24
 * @description
 */

public class ParallaxListView extends ListView {
    private int headerHeight = 0;
    private View header;

    public ParallaxListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        headerHeight = getResources().getDimensionPixelOffset(R.dimen.image_height);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (header != null) {
            if (deltaY < 0) {//下拉过度
                header.getLayoutParams().height = header.getHeight() - deltaY;
                header.requestLayout();
                System.out.println("下拉过度");
            } else if (header.getHeight() > headerHeight) {//上拉过度
                header.getLayoutParams().height = header.getHeight() - deltaY;
                header.requestLayout();
                System.out.println("上拉过度");
            }
        }
        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        if (header != null) {
            View parent = (View) header.getParent();
            System.out.println("parent.getTop()==" + parent.getTop());
            System.out.println("headerParent.getHeight()=" + header.getHeight());
            if (parent.getTop() < 0 && parent.getHeight() > headerHeight) {
                header.getLayoutParams().height = header.getHeight() + parent.getTop();
                parent.layout(parent.getLeft(), 0, parent.getRight(), parent.getHeight());
                header.requestLayout();
            }
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_UP){
            MyAnimation animation = new MyAnimation(header,headerHeight);
            animation.setDuration(300);
            header.startAnimation(animation);
        }
        return super.onTouchEvent(ev);
    }

    public void setHeader(View header) {
        this.header = header;
    }

    class MyAnimation extends Animation {
        private View header;
        private int targetHeight;
        private int originHeight;

        public MyAnimation(View header, int targetHeight) {
            this.header = header;
            this.targetHeight = targetHeight;
            this.originHeight = header.getHeight();
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            header.getLayoutParams().height = (int) (originHeight - (originHeight - targetHeight) * interpolatedTime);
            header.requestLayout();
            super.applyTransformation(interpolatedTime, t);
        }
    }
}
