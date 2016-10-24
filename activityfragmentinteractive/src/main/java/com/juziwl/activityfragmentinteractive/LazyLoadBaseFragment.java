package com.juziwl.activityfragmentinteractive;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wy
 * @version V_5.0.0
 * @date 2016/3/1
 * @description 基类fragment
 */
public abstract class LazyLoadBaseFragment extends Fragment {
    public String TAG = this.getClass().getSimpleName();
    public String startTime = "";
    public String endTime = "";
    public List<String> functionName = new ArrayList<>();
    public String uid = "";
    public String token = "";
    public boolean hideflag = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * Fragment当前状态是否可见 使用setUserVisibleHint，这个是每次都会调用的
     */
    protected boolean isVisible;
    protected Handler mHandler = null;
    protected boolean canOpen = true;//用来防止多次点击打开多个页面

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }


    /**
     * 可见
     */
    protected void onVisible() {
        lazyLoad();
    }

    /**
     * 不可见
     */
    protected void onInvisible() {
    }

    /**
     * 延迟加载 子类必须重写此方法
     */
    protected abstract void lazyLoad();

    public abstract void stateUpdate();

    @Override
    public void onResume() {
        super.onResume();
        canOpen = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (hideflag) {
        } else {
        }
    }
}
