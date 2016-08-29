package com.example.myannotation.annotation;

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
@Target(ElementType.ANNOTATION_TYPE)
public @interface EventBase {

    String listenerSetter();//方法名字

    Class<?> listenerType();//监听的类型

    String callbackMethod();//调用的监听事件里的方法名
}
