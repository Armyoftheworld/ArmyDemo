package com.juziwl.autosmsinput;

import android.Manifest;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.CompositePermissionListener;

public class MainActivity extends AppCompatActivity {

    private EditText edittext;
    private SmsObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edittext = (EditText) findViewById(R.id.edittext);
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == SmsObserver.SMSRECRIVERCODE) {
                    String[] arr = (String[]) msg.obj;
                    edittext.setText("address = " + arr[0] + "\nbody = " + arr[1]);
                }
            }
        };
        //如果权限被禁止了，会怎么样？
        Dexter.initialize(this);
        Dexter.checkPermission(new CompositePermissionListener(), Manifest.permission.READ_SMS);
        observer = new SmsObserver(this, handler);
        observer.registerContentObserver();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        observer.unRegisterContentObserver();
    }
}
