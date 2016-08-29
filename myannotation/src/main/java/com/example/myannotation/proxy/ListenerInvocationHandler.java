package com.example.myannotation.proxy;

import android.app.Activity;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/8/29
 * @description
 */
public class ListenerInvocationHandler implements InvocationHandler {

    private Activity activity;
    private Map<String, Method> methodMap;

    public ListenerInvocationHandler(Activity activity, Map<String, Method> methodMap) {
        this.activity = activity;
        this.methodMap = methodMap;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //method就是我们代理的监听里的方法
        String name = method.getName();
        //调用自己的方法
        Method mtd = methodMap.get(name);
        if (mtd != null) {
            return mtd.invoke(activity, args);
        }
        return method.invoke(activity, args);
    }
}
