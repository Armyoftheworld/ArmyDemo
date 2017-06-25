package com.example.myannotation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myannotation.annotation.ContextView;
import com.example.myannotation.annotation.OnClick;
import com.example.myannotation.annotation.ViewInject;

@ContextView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(R.id.text)
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textView.setText("Toast");
    }

    @OnClick({R.id.text})
    public void show(View view) {
        Toast.makeText(this, "Toast", Toast.LENGTH_SHORT).show();
    }

}
