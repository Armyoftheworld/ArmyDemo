package com.juziwl.mvp.activity;

import android.app.Activity;
import android.os.Bundle;

import com.juziwl.mvp.presenter.BasePresenter;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/27
 * @description 应用程序Activity的模板类
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends Activity {
    protected T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
        presenter.attachView((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detachView();
    }

    protected abstract T createPresenter();

    protected abstract void initView();

    protected abstract void findViewById();
}
