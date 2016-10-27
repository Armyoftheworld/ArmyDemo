package com.juziwl.mvp.model.interfaces;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/27
 * @description 应用程序Activity的模板类
 */

public interface BaseModel<T> {
    void loadData(OnDataLoadListener<T> listener);
}
