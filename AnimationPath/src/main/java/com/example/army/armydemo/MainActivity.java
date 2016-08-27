package com.example.army.armydemo;

import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        view = findViewById(R.id.view);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doAnimation();
            }
        });
    }

    public void doAnimation() {
        final AnimationPath anim = new AnimationPath();
        anim.moveTo(600, 0);
        anim.cubicTo(400, 400, 120, 120, 100, 100);

        ObjectAnimator animator = ObjectAnimator.ofObject(this, "pointLoc", new TypeEvaluator<PointPath>() {
            @Override
            public PointPath evaluate(float fraction, PointPath startValue, PointPath endValue) {
                float mx, my;
                if (endValue.mOperation == PointPath.LINE) {
                    mx = startValue.mx + (endValue.mx - startValue.mx) * fraction;
                    my = startValue.my + (endValue.my - startValue.my) * fraction;
                } else if (endValue.mOperation == PointPath.MOVE) {
                    mx = endValue.mx;
                    my = endValue.my;
                } else {
                    //贝塞尔曲线的公式
                    float flag = 1 - fraction;
                    mx = startValue.mx * flag * flag * flag + 3 * endValue.mx * flag * flag * fraction +
                            3 * endValue.cx0 * fraction * fraction * flag + endValue.cx1 * fraction * fraction * fraction;
                    my = startValue.my * flag * flag * flag + 3 * endValue.my * flag * flag * fraction +
                            3 * endValue.cy0 * fraction * fraction * flag + endValue.cy1 * fraction * fraction * fraction;
                }
                return PointPath.moveTo(mx, my);
            }
        }, anim.getPointPaths().toArray());
        animator.setDuration(800);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (animation.getAnimatedValue() instanceof PointPath) {
                    PointPath pointPath = (PointPath) animation.getAnimatedValue();
                    if (600 - pointPath.mx < 400) {
                        view.animate().scaleXBy(80).scaleYBy(80).setInterpolator(new LinearInterpolator()).setDuration(200).start();
                    }
                }
            }
        });
        animator.start();
    }

    public void setPointLoc(PointPath pointLoc) {
        textView.setTranslationX(pointLoc.mx);
        textView.setTranslationY(pointLoc.my);
    }

}
