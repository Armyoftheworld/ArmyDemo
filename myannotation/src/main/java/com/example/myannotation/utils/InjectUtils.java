package com.example.myannotation.utils;

import android.app.Activity;
import android.view.View;

import com.example.myannotation.annotation.ContextView;
import com.example.myannotation.annotation.EventBase;
import com.example.myannotation.annotation.ViewInject;
import com.example.myannotation.proxy.ListenerInvocationHandler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/8/29
 * @description
 */
public class InjectUtils {

    public static void init(Activity activity) {
        initLayout(activity);
        initField(activity);
        try {
            initClick(activity);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private static void initClick(Activity activity) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<?> annotationType = annotation.annotationType();
                EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                if (eventBase != null) {
                    String listenerSetter = eventBase.listenerSetter();
                    Class<?> listenerType = eventBase.listenerType();
                    String callbackMethod = eventBase.callbackMethod();

                    Method valuemethod = annotationType.getDeclaredMethod("value");
                    int[] viewIds = (int[]) valuemethod.invoke(annotation);

                    for (int viewId : viewIds) {
                        View view = activity.findViewById(viewId);
                        if (view == null)
                            continue;
                        Method setListenerMethod = View.class.getMethod(listenerSetter, listenerType);
                        Map<String, Method> methodMap = new HashMap<>();
                        methodMap.put(callbackMethod, method);
                        InvocationHandler handler = new ListenerInvocationHandler(activity, methodMap);
                        Object proxy = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class[]{listenerType}, handler);
                        setListenerMethod.invoke(view, proxy);
                    }
                }


            }
        }
    }

    private static void initField(Activity activity) {
        Class cla = activity.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            ViewInject viewInject = field.getAnnotation(ViewInject.class);
            if (viewInject != null) {
                int viewId = viewInject.value();
                View view = activity.findViewById(viewId);
                try {
                    field.setAccessible(true);
                    field.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private static void initLayout(Activity activity) {
        Class<?> cla = activity.getClass();
        ContextView contextView = cla.getAnnotation(ContextView.class);
        int layoutId = contextView.value();
        activity.setContentView(layoutId);
    }
}
