package com.juziwl.activityfragmentinteractive;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class SimpleFragmentActivity extends AppCompatActivity implements OnActivityUseFragmentMethodListener {
    private OnActivityUseFragmentMethodListener onActivityUseFragmentMethodListener;
    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.container, new MyFragment());
        fragmentTransaction.commit();
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        if (fragment instanceof OnActivityUseFragmentMethodListener) {
            onActivityUseFragmentMethodListener = (OnActivityUseFragmentMethodListener) fragment;
        }
        super.onAttachFragment(fragment);
    }

    public void onClick(View v) {
        if (onActivityUseFragmentMethodListener != null) {
            onActivityUseFragmentMethodListener.onActivityUseFragmentMethod(Math.random() * 1000 + "");
        }
    }

    @Override
    public void onActivityUseFragmentMethod(String content) {
        text.setText("receive data:" + content);
    }
}
