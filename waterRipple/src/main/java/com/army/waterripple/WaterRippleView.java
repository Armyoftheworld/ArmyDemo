package com.army.waterripple;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/7/15
 * @description 波浪
 */
public class WaterRippleView extends View {
    private Paint paint;
    private int waveWidth = 0;
    private int[] waveHeights;
    private int waveCount = 1;
    private int[] waveColors;
    private int width = 0, height = 0;
    private Path[] paths;
    private int offset = 0, offset2 = 0;
    private ValueAnimator animator, animator2;

    public WaterRippleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        init();
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.WaterRippleView);
        int waveHeight = array.getDimensionPixelOffset(R.styleable.WaterRippleView_waveHeight, dp2px(50));
        waveWidth = array.getDimensionPixelOffset(R.styleable.WaterRippleView_waveWidth, dp2px(150));
        waveCount = array.getInt(R.styleable.WaterRippleView_waveCount, 1);
        String colorsStr = array.getString(R.styleable.WaterRippleView_waveColors);
        waveColors = new int[waveCount];
        waveHeights = new int[waveCount];
        if (waveCount == 1) {
            waveHeights[0] = waveHeight;
        } else {
            waveHeights[0] = waveHeight - dp2px(4);
            waveHeights[1] = waveHeight;
        }
        if (!TextUtils.isEmpty(colorsStr)) {
            String[] colors = colorsStr.split(";");
            if (colors.length != waveCount) {
                throw new IllegalStateException("color's count must equals waveCount");
            } else {
                for (int i = 0; i < colors.length; i++) {
                    waveColors[i] = Color.parseColor(colors[i]);
                }
            }
        } else {
            waveColors[0] = Color.CYAN;
        }
        array.recycle();
    }


    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        paths = new Path[waveCount];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width == 0 || height == 0) return;

        canvas.translate(0, height / 2);
        for (int i = 0; i < waveCount; i++) {
            Path path = paths[i];
            if (path == null) {
                path = new Path();
            } else {
                path.reset();
            }
            int waveHeight = waveHeights[i];
            paint.setColor(waveColors[i]);
            int firstX = -waveWidth + (i == 0 ? offset : offset2) - i * waveWidth / 2;
            path.moveTo(firstX, 0);
            for (int j = firstX; j < waveWidth + width; j += waveWidth) {
                path.rQuadTo(waveWidth / 4, -waveHeight / 2, waveWidth / 2, 0);
                path.rQuadTo(waveWidth / 4, waveHeight / 2, waveWidth / 2, 0);
            }
            path.rLineTo(0, height / 2);
            path.lineTo(firstX, height / 2);
            path.close();
            canvas.drawPath(path, paint);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (width == 0) {
            width = w;
            height = h;
            startWave();
        }
    }

    private int dp2px(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp + 0.5);
    }

    public void startWave() {
        if (animator == null) {
            animator = ValueAnimator.ofInt(0, waveWidth);
        } else {
            animator.cancel();
        }
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setDuration(1500);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offset = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();

        if (waveCount == 2) {
            if (animator2 == null) {
                animator2 = ValueAnimator.ofInt(0, waveWidth);
            } else {
                animator2.cancel();
            }
            animator2.setRepeatCount(ValueAnimator.INFINITE);
            animator2.setInterpolator(new LinearInterpolator());
            animator2.setDuration(2500);
            animator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    offset2 = (int) animation.getAnimatedValue();
                }
            });
            animator2.start();
        }
    }

    public void stopWave() {
        if (animator != null) {
            animator.cancel();
        }
        if (animator2 != null) {
            animator2.cancel();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stopWave();
    }
}
