package com.army.parallaxscrollview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;

public class MainActivity extends AppCompatActivity {
    private ParallaxScrollView parallaxScrollView;
    private ParallaxListView listview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parallaxScrollView = (ParallaxScrollView) findViewById(R.id.scrollView);
        listview = (ParallaxListView) findViewById(R.id.listview);
        parallaxScrollView.setHeaderImage(findViewById(R.id.image));
        View header = getLayoutInflater().inflate(R.layout.header,null);
        listview.setHeader(header.findViewById(R.id.image));
        listview.addHeaderView(header);
        listview.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,new String[]{
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView",
                "ParallaxListView"
        }));
    }

    public void exchange(View view){
        if(listview.getVisibility() ==View.GONE){
            listview.setVisibility(View.VISIBLE);
            parallaxScrollView.setVisibility(View.GONE);
        }else {
            listview.setVisibility(View.GONE);
            parallaxScrollView.setVisibility(View.VISIBLE);
        }
    }
}
