package com.army.waterripple;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/7/15
 * @description
 */
public class MyProgressBar extends View {
    private int width = 0, height = 0;
    private Paint paint;
    private float textWidth = 0;
    private int textHeight = 0;
    private Rect getTextBounds = new Rect();
    private RectF bgProgressRectf = new RectF(), progressRectf = new RectF();
    private int bgProgressColor = Color.parseColor("#f5f7fa");
    private int progressStartColor, progressEndColor;
    private LinearGradient linearGradient;
    private int padding = dp2px(2);
    private int progress = 0, maxProgress = 0;
    private String measureString;
    private int unReachHalfTextColor = Color.parseColor("#333333"),
            reachHalfTextColor = Color.WHITE;


    public MyProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextAlign(Paint.Align.CENTER);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MyProgressBar);
        progress = array.getInt(R.styleable.MyProgressBar_progress, 0);
        maxProgress = array.getInt(R.styleable.MyProgressBar_maxProgress, 100);
        bgProgressColor = array.getColor(R.styleable.MyProgressBar_bgProgressColor, Color.parseColor("#f5f7fa"));
        progressStartColor = array.getColor(R.styleable.MyProgressBar_progressStartColor, Color.parseColor("#46ccff"));
        progressEndColor = array.getColor(R.styleable.MyProgressBar_progressEndColor, Color.parseColor("#466eff"));
        measureString = array.getString(R.styleable.MyProgressBar_measureString);
        if (TextUtils.isEmpty(measureString)) {
            measureString = "50%";
        }
        array.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (width == 0) return;

        canvas.translate(width / 2, height / 2);

        //画背景进度条
        paint.setColor(bgProgressColor);
        paint.setStyle(Paint.Style.FILL);
        float progressRight = getProgressRight();

        float bgProgressLeft = progressRight - height / 2;
        if (bgProgressLeft < -width / 2 + padding) {
            bgProgressLeft = -width / 2 + padding;
        }
        bgProgressRectf.left = bgProgressLeft;
        canvas.drawRoundRect(bgProgressRectf, height, height, paint);

        //画进度条
        paint.setShader(linearGradient);
        progressRectf.right = progressRight;
        canvas.drawRoundRect(progressRectf, height, height, paint);
        paint.setShader(null);

        //画文字
        paint.setStyle(Paint.Style.STROKE);
        float halfTextWidth = textWidth / 2f;
        if (progressRight <= -halfTextWidth) {
            drawLeftOrRightOutText(canvas, unReachHalfTextColor);
        } else if (progressRight > -halfTextWidth && progressRight <= halfTextWidth) {
            drawClipText(canvas, -halfTextWidth, progressRight, reachHalfTextColor);
            drawClipText(canvas, progressRight, halfTextWidth, unReachHalfTextColor);
        } else {
            drawLeftOrRightOutText(canvas, reachHalfTextColor);
        }
    }

    private void drawClipText(Canvas canvas, float start, float end, int textColor) {
        paint.setColor(textColor);
        canvas.save(Canvas.CLIP_SAVE_FLAG);
        canvas.clipRect(start, -textHeight / 2, end, textHeight / 2);
        canvas.drawText(progress + "%", 0, textHeight / 2, paint);
        canvas.restore();
    }

    private void drawLeftOrRightOutText(Canvas canvas, int textColor) {
        paint.setColor(textColor);
        canvas.drawText(progress + "%", 0, textHeight / 2, paint);
    }

    private float getProgressRight() {
        float percent = progress * 1.0f / maxProgress;
        return (width - 2 * padding) * percent - (width / 2 - padding);
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        postInvalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (width == 0) {
            width = w;
            height = h;
            String text = measureString;
            paint.setTextSize(height - dp2px(4));
            textWidth = paint.measureText(text);
            paint.getTextBounds(text, 0, text.length(), getTextBounds);
            textHeight = getTextBounds.bottom - getTextBounds.top;

            bgProgressRectf.top = -height / 2;
            bgProgressRectf.right = width / 2 - padding;
            bgProgressRectf.bottom = height / 2;

            progressRectf.left = -width / 2 + padding;
            progressRectf.top = -height / 2;
            progressRectf.bottom = height / 2;

            linearGradient = new LinearGradient(-width / 2, 0, width / 2, 0, progressStartColor, progressEndColor, Shader.TileMode.CLAMP);
        }
    }


    private int dp2px(int dp) {
        return (int) (getResources().getDisplayMetrics().density * dp + 0.5);
    }
}
