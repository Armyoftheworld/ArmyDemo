package dn.sindy.waterfalls;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.*;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().clearDiskCache();
        com.nostra13.universalimageloader.core.ImageLoader.getInstance().clearMemoryCache();
        setContentView(R.layout.activity_main);
    }


}
