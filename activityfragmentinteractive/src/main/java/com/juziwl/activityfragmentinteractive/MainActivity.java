package com.juziwl.activityfragmentinteractive;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void SimpleFragmentActivity(View view) {
        Intent intent = new Intent(this, SimpleFragmentActivity.class);
        startActivity(intent);
    }

    public void FragmentViewPager(View view) {
        Intent intent = new Intent(this, FragmentViewPagerActivity.class);
        startActivity(intent);
    }
}
