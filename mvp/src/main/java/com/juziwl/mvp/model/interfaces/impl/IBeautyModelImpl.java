package com.juziwl.mvp.model.interfaces.impl;

import android.os.Handler;
import android.os.Looper;

import com.juziwl.mvp.R;
import com.juziwl.mvp.model.Beauty;
import com.juziwl.mvp.model.interfaces.BaseModel;
import com.juziwl.mvp.model.interfaces.OnDataLoadListener;

import java.util.ArrayList;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/27
 * @description 应用程序Activity的模板类
 */

public class IBeautyModelImpl implements BaseModel<ArrayList<Beauty>> {
    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void loadData(final OnDataLoadListener<ArrayList<Beauty>> listener) {
        new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    final ArrayList<Beauty> beauties = new ArrayList<>();
                    beauties.add(new Beauty("张雨绮1", R.mipmap.beauty2, "好漂亮啊1"));
                    beauties.add(new Beauty("张雨绮2", R.mipmap.beauty2, "好漂亮啊2"));
                    beauties.add(new Beauty("张雨绮3", R.mipmap.beauty2, "好漂亮啊3"));
                    beauties.add(new Beauty("张雨绮4", R.mipmap.beauty2, "好漂亮啊4"));
                    beauties.add(new Beauty("张雨绮5", R.mipmap.beauty2, "好漂亮啊5"));
                    beauties.add(new Beauty("张雨绮6", R.mipmap.beauty2, "好漂亮啊6"));
                    beauties.add(new Beauty("张雨绮7", R.mipmap.beauty2, "好漂亮啊7"));
                    beauties.add(new Beauty("张雨绮8", R.mipmap.beauty2, "好漂亮啊8"));
                    beauties.add(new Beauty("张雨绮9", R.mipmap.beauty2, "好漂亮啊9"));
                    beauties.add(new Beauty("张雨绮10", R.mipmap.beauty2, "好漂亮啊10"));
                    beauties.add(new Beauty("张雨绮11", R.mipmap.beauty2, "好漂亮啊11"));
                    beauties.add(new Beauty("张雨绮12", R.mipmap.beauty2, "好漂亮啊12"));
                    beauties.add(new Beauty("张雨绮13", R.mipmap.beauty2, "好漂亮啊13"));
                    beauties.add(new Beauty("张雨绮14", R.mipmap.beauty2, "好漂亮啊14"));
                    beauties.add(new Beauty("张雨绮15", R.mipmap.beauty2, "好漂亮啊15"));
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onComplete(beauties);
                        }
                    });
                } catch (final Exception e) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onFailure(e);
                        }
                    });
                }
            }
        }.start();
    }
}
