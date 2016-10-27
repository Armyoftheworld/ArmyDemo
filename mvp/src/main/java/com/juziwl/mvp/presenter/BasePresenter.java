package com.juziwl.mvp.presenter;

import java.lang.ref.WeakReference;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/27
 * @description 应用程序Activity的模板类
 */

public abstract class BasePresenter<T> {
    protected WeakReference<T> mViewRef;

    public void attachView(T view) {
        mViewRef = new WeakReference<>(view);
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
        }
    }

    public T getView() {
        if (mViewRef != null) {
            return mViewRef.get();
        }
        return null;
    }

    protected abstract void initData();

}
