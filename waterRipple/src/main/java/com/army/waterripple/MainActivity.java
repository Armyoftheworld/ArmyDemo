package com.army.waterripple;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        WaterRippleView waterRippleView = (WaterRippleView) findViewById(R.id.wave);
//        waterRippleView.startWave();
        final MyProgressBar progressbar = (MyProgressBar) findViewById(R.id.progressbar);
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                progressbar.setProgress(progressbar.getProgress() + 1);
                if(progressbar.getProgress() == 100){
                    cancel();
                    timer.cancel();
                }
            }
        };
        timer.schedule(timerTask, 1000, 200);
    }
}
