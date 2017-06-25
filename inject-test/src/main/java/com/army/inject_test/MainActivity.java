package com.army.inject_test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import com.army.annotation.BindView;
import com.army.inject.InjectView;
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.textview)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InjectView.bind(this);
        Toast.makeText(this, "-->" + textView, Toast.LENGTH_SHORT).show();
    }
}
