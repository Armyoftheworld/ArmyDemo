package com.army.inject;

import android.app.Activity;

/**
 * Created by Army on 2017/06/25.
 */

public class InjectView {

    public static void bind(Activity activity){
        String name = activity.getClass().getName();
        try {
            Class<?> aClass = Class.forName(name + "$$ViewBinder");
            ViewBinder instance = (ViewBinder) aClass.newInstance();
            instance.bind(activity);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
