package com.juziwl.mvp.presenter;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/27
 * @description 应用程序Activity的模板类
 */

public abstract class RefreshLoadingPresenter<T> extends BasePresenter<T> {
    protected abstract void onRefresh();

    protected abstract void onLoading();
}
