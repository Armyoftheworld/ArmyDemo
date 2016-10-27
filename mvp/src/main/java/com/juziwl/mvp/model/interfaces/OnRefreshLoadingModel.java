package com.juziwl.mvp.model.interfaces;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/27
 * @description 应用程序Activity的模板类
 */

public interface OnRefreshLoadingModel<T> extends BaseModel<T> {
    void onRefresh(OnDataLoadListener<T> listener);

    void onLoading(OnDataLoadListener<T> listener);
}
