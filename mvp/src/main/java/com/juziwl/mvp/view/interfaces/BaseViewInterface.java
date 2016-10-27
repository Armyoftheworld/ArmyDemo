package com.juziwl.mvp.view.interfaces;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/27
 * @description 应用程序Activity的模板类
 */

public interface BaseViewInterface<T> {
    void showData(T data);

    void showDialog();

    void dismissDialog();

    /**
     *
     * @param e
     * @param flag 自定义类型，用来区分多种处理方式
     */
    void handleError(Throwable e,int flag);
}
