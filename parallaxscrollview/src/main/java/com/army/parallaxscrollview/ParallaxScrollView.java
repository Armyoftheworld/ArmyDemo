package com.army.parallaxscrollview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ScrollView;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/10/24
 * @description
 */

public class ParallaxScrollView extends ScrollView {
    private View headerParent, headerImage;
    private int headerHeight = 0;

    public ParallaxScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        headerHeight = getResources().getDimensionPixelOffset(R.dimen.image_height);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        if (deltaY < 0) {//下拉过度
            System.out.println("下拉过度");
            headerImage.getLayoutParams().height = headerImage.getHeight() - deltaY;
            headerImage.requestLayout();
        } else if (headerImage.getHeight() > headerHeight) {//上拉过度
            System.out.println("上拉过度");
            headerImage.getLayoutParams().height = (int) (headerImage.getHeight() - deltaY * 1.5);
            headerImage.requestLayout();
        }

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    public void setOnScrollChangeListener(OnScrollChangeListener l) {
        super.setOnScrollChangeListener(l);
    }

    @Override
    protected void onScrollChanged(int w, int h, int oldw, int oldh) {
//        System.out.println("h = " + h + "\toldh = " + oldh);
//        System.out.println("headerParent.getTop()==" + headerParent.getTop());
//        System.out.println("headerImage.getTop()==" + headerImage.getTop());
//        System.out.println("((View)headerParent.getParent()).getTop()==" + ((View) headerParent.getParent()).getTop());
        System.out.println("getScrollY()==" + getScrollY());
        System.out.println("headerImage.getHeight()==" + headerImage.getHeight());
//        System.out.println("headerHeight=="+headerHeight);
        if (headerImage.getHeight() > headerHeight && headerParent.getTop() < 0) {
            System.out.println("if (headerParent.getTop() < 0 && headerImage.getHeight() > headerHeight)");
            headerImage.getLayoutParams().height = headerImage.getHeight() + headerParent.getTop();
            System.out.println("headerParent.getHeight()=" + headerParent.getHeight());
            headerParent.layout(headerParent.getLeft(), 0, headerParent.getRight(), headerParent.getHeight());
            headerImage.requestLayout();
        }
        super.onScrollChanged(w, h, oldw, oldh);
    }

    public void setHeaderImage(View headerImage) {
        this.headerImage = headerImage;
        this.headerParent = (View) headerImage.getParent();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_UP){
            MyAnimation animation = new MyAnimation(headerImage,headerHeight);
            animation.setDuration(300);
            headerImage.startAnimation(animation);
        }
        return super.onTouchEvent(ev);
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
            headerImage.getLayoutParams().height = (int) (originHeight - (originHeight - targetHeight) * interpolatedTime);
            headerImage.requestLayout();
            super.applyTransformation(interpolatedTime,t);
        }
    }
}
