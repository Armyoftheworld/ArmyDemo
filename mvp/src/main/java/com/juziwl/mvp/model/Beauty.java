package com.juziwl.mvp.model;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/27
 * @description 应用程序Activity的模板类
 */

public class Beauty {
    public String name = "";
    public int resId = 0;
    public String desc = "";

    public Beauty(String name, int resId, String desc) {
        this.name = name;
        this.resId = resId;
        this.desc = desc;
    }
}
