package com.juziwl.mvp.presenter.impl;

import com.juziwl.mvp.R;
import com.juziwl.mvp.config.MyApplication;
import com.juziwl.mvp.model.Beauty;
import com.juziwl.mvp.model.interfaces.OnDataLoadListener;
import com.juziwl.mvp.model.interfaces.impl.IBeautyModelImpl2;
import com.juziwl.mvp.presenter.RefreshLoadingPresenter;
import com.juziwl.mvp.utils.CommonUtils;
import com.juziwl.mvp.utils.NetworkUtils;
import com.juziwl.mvp.view.interfaces.IBeautyView2;

import java.util.ArrayList;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/27
 * @description 应用程序Activity的模板类
 */

public class IBeautyPresenter2 extends RefreshLoadingPresenter<IBeautyView2> {
    private IBeautyModelImpl2 iBeautyModel = new IBeautyModelImpl2();

    @Override
    public void onRefresh() {
        if (NetworkUtils.isNetworkAvailable(MyApplication.getContext())) {
            iBeautyModel.onRefresh(new OnDataLoadListener<ArrayList<Beauty>>() {
                @Override
                public void onComplete(ArrayList<Beauty> data) {
                    getView().onRefresh(data);
                }

                @Override
                public void onFailure(Throwable ex) {
                    getView().handleError(ex, 1);
                }
            });
        } else {
            CommonUtils.showToast(R.string.useless_network);
        }
    }

    @Override
    public void onLoading() {
        if (NetworkUtils.isNetworkAvailable(MyApplication.getContext())) {
            iBeautyModel.onLoading(new OnDataLoadListener<ArrayList<Beauty>>() {
                @Override
                public void onComplete(ArrayList<Beauty> data) {
                    getView().showData(data);
                }

                @Override
                public void onFailure(Throwable ex) {
                    getView().handleError(ex, 2);
                }
            });
        } else {
            CommonUtils.showToast(R.string.useless_network);
        }
    }

    @Override
    public void initData() {
        if (NetworkUtils.isNetworkAvailable(MyApplication.getContext())) {
            getView().showDialog();
            iBeautyModel.loadData(new OnDataLoadListener<ArrayList<Beauty>>() {
                @Override
                public void onComplete(ArrayList<Beauty> data) {
                    getView().dismissDialog();
                    getView().showData(data);
                }

                @Override
                public void onFailure(Throwable ex) {
                    getView().handleError(ex, 0);
                }
            });
        } else {
            CommonUtils.showToast(R.string.useless_network);
        }
    }
}
