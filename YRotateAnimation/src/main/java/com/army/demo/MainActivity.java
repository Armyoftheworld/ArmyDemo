package com.army.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;


/**
 *
 * @author 亦枫，微信公众号：技术鸟
 */
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View image = findViewById(R.id.image);
        MyYAnimation myYAnimation = new MyYAnimation();
        myYAnimation.setRepeatCount(Animation.INFINITE); //旋转的次数（无数次）
        Animation translate = AnimationUtils.loadAnimation(this, R.anim.translate);
        translate.setRepeatCount(Animation.INFINITE);
        translate.setRepeatMode(Animation.REVERSE);
        AnimationSet animationSet = new AnimationSet(true);
        animationSet.addAnimation(myYAnimation);
        animationSet.addAnimation(translate);
        image.startAnimation(animationSet);

    }
}
