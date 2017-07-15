package com.army.autoinstallapp;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/5/29
 * @description
 */
public class MyAccessibilityService extends AccessibilityService {

    private static final String TAG = "[TAG]";
    private Map<Integer, Boolean> handleMap = new HashMap<>();
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean isCanClick = false;

    /**
     * 当进入apk安装界面就会回调onAccessibilityEvent()这个方法，我们只关心TYPE_WINDOW_CONTENT_CHANGED和TYPE_WINDOW_STATE_CHANGED两个事件
     *
     * @param event
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo nodeInfo = event.getSource();
        if (nodeInfo != null) {
            int eventType = event.getEventType();
            if (eventType == AccessibilityEvent.TYPE_WINDOW_CONTENT_CHANGED || eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
//                if (handleMap.get(event.getWindowId()) == null) {
                    iterateNodesAndHandle(nodeInfo);
//                    if (handled) {
//                        handleMap.put(event.getWindowId(), true);
//                    }
//                }
            }

        }
    }

    @Override
    public void onInterrupt() {

    }

    //遍历节点，模拟点击安装按钮
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void iterateNodesAndHandle(final AccessibilityNodeInfo nodeInfo) {
        System.out.println("isCanClick = " + isCanClick);
        if (nodeInfo != null) {
            int childCount = nodeInfo.getChildCount();
//            Logger.d("nodeInfo = " + nodeInfo.toString());
            if ("android.widget.TextView".equals(nodeInfo.getClassName())){
                if("AutoInstallApp".equals(nodeInfo.getText().toString())){
                    isCanClick = true;
                }
            }
            if ("android.widget.Button".equals(nodeInfo.getClassName())) {
                String nodeCotent = nodeInfo.getText().toString();
                if ("安装".equals(nodeCotent) && isCanClick) {
                    nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
                System.out.println(nodeCotent);
                if(("打开".equals(nodeCotent)) && isCanClick){
                    isCanClick = false;
                    System.out.println("open");
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                        }
                    }, 1000);

                }
            }
            //遇到ScrollView的时候模拟滑动一下
            else if ("android.widget.ScrollView".equals(nodeInfo.getClassName())) {
                nodeInfo.performAction(AccessibilityNodeInfo.ACTION_SCROLL_FORWARD);
            }
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo childNodeInfo = nodeInfo.getChild(i);
                iterateNodesAndHandle(childNodeInfo);
            }
        }
    }
}
