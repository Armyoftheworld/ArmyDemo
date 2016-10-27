package com.juziwl.mvp.view.interfaces;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/27
 * @description 应用程序Activity的模板类
 */

public interface RefreshLoadingViewInterface<T> extends BaseViewInterface<T> {
    void onRefresh(T data);

    void onLoading(T data);

}
