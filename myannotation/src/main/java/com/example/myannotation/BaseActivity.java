package com.example.myannotation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myannotation.utils.InjectUtils;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/8/29
 * @description
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectUtils.init(this);
    }
}
