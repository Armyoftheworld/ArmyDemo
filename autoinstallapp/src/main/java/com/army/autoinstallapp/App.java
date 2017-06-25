package com.army.autoinstallapp;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import com.orhanobut.logger.LogAdapter;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2017/5/29
 * @description
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("autoinstallapp").logLevel(LogLevel.FULL)
                .logAdapter(new LogAdapter() {

                    private void write2File(String content) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        String today = format.format(new Date());
                        FileUtils.appendToFile("\n" + content, Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + today + ".txt");
                    }

                    @Override
                    public void d(String tag, String message) {
                        Log.d(tag, message);
                        write2File(message);
                    }

                    @Override
                    public void e(String tag, String message) {
                        Log.e(tag, message);
                        write2File(message);
                    }

                    @Override
                    public void w(String tag, String message) {
                        Log.w(tag, message);
                        write2File(message);
                    }

                    @Override
                    public void i(String tag, String message) {
                        Log.i(tag, message);
                        write2File(message);
                    }

                    @Override
                    public void v(String tag, String message) {
                        Log.v(tag, message);
                        write2File(message);
                    }

                    @Override
                    public void wtf(String tag, String message) {
                        Log.wtf(tag, message);
                        write2File(message);
                    }
                }).hideThreadInfo().methodCount(0);
    }
}
