package com.juziwl.mvp.presenter.impl;

import com.juziwl.mvp.R;
import com.juziwl.mvp.config.MyApplication;
import com.juziwl.mvp.model.Beauty;
import com.juziwl.mvp.model.interfaces.BaseModel;
import com.juziwl.mvp.model.interfaces.OnDataLoadListener;
import com.juziwl.mvp.model.interfaces.impl.IBeautyModelImpl;
import com.juziwl.mvp.presenter.BasePresenter;
import com.juziwl.mvp.utils.CommonUtils;
import com.juziwl.mvp.utils.NetworkUtils;
import com.juziwl.mvp.view.interfaces.IBeautyView;

import java.util.ArrayList;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/27
 * @description 应用程序Activity的模板类
 */

public class IBeautyPresenter extends BasePresenter<IBeautyView> {
    private BaseModel iBeautyModel = new IBeautyModelImpl();

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
