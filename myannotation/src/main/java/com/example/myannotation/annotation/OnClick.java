package com.example.myannotation.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/8/29
 * @description
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@EventBase(listenerSetter = "setOnClickListener",listenerType = View.OnClickListener.class,callbackMethod = "onClick")
public @interface OnClick {
    int[] value();
}
