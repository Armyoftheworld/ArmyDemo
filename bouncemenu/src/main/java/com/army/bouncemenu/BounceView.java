package com.army.bouncemenu;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/10/29
 * @description
 */

public class BounceView extends View {
    private Path path;
    private Paint paint;
    private int maxArcHeight;
    private int curArcHeight = 0;
    private final int NONE = 0;
    private final int SMOOTH_UP = 1;
    private final int DOWN = 2;
    private int mState = NONE;
    private int currentY;

    public BounceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        maxArcHeight = getResources().getDimensionPixelOffset(R.dimen.activity_horizontal_margin) * 3;
        System.out.println("maxArcHeight==" + maxArcHeight);
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        switch (mState) {
            case NONE:
                currentY = maxArcHeight;
                break;
            case SMOOTH_UP:
                //跟curArcHeight变化的百分比是一样的
                currentY = getHeight() - (int) ((getHeight() - maxArcHeight) * Math.min(1, (curArcHeight - maxArcHeight / 4) * 2.0 / maxArcHeight * 1.3));
                System.out.println("currentY==" + currentY);
                break;
            case DOWN:
                currentY = getHeight() - (int) ((getHeight() - maxArcHeight) * Math.min(1, (maxArcHeight - maxArcHeight / 4) * 2.0 / maxArcHeight * 1.3));
                break;
        }
        path.moveTo(0, currentY);
        path.quadTo(getWidth() / 2, currentY - curArcHeight, getWidth(), currentY);
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.close();
        canvas.drawPath(path, paint);
    }

    public void show() {
        mState = SMOOTH_UP;
        if (listener != null) {
            listener.onStart();
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    listener.onContentShow();
                }
            }, 500);
        }
        ValueAnimator animator = ValueAnimator.ofInt(0, maxArcHeight);
        animator.setDuration(800);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curArcHeight = (int) animation.getAnimatedValue();
                if (curArcHeight == maxArcHeight) {
                    bounce();
                }
                invalidate();
            }
        });
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.start();
    }

    private void bounce() {
        mState = DOWN;
        ValueAnimator animator = ValueAnimator.ofInt(maxArcHeight, 0);
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                curArcHeight = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    private BounceAnimationListener listener;

    public void setBounceAnimationListener(BounceAnimationListener listener) {
        this.listener = listener;
    }

    public interface BounceAnimationListener {
        void onStart();

        void onEnd();

        void onContentShow();
    }
}
