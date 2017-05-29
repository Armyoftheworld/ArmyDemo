package com.army.autoinstallapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.orhanobut.logger.Logger;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/5/29
 * @description
 */
public class AppInstallFinishReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, intent.toString(), Toast.LENGTH_LONG).show();
        Logger.d(intent);
    }
}
