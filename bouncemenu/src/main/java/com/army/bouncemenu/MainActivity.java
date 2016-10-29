package com.army.bouncemenu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private BounceMenu bounceMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.setting:
                if (bounceMenu != null && bounceMenu.isShow()) {
                    bounceMenu.dismiss();
                } else {
                    bounceMenu = BounceMenu.make(this, R.layout.layout_bounceview, findViewById(R.id.activity_main), new MyAdapter()).show();
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public void onClick(View view){
        Toast.makeText(getApplicationContext(),"Test",Toast.LENGTH_SHORT).show();
    }
}
