package com.example.scorllbarpanel;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SweepPanelListView.onPositionChangedListener {
    private SweepPanelListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (SweepPanelListView) findViewById(R.id.listview);
        listView.setAdapter(new DummyAdapter());
        listView.setCacheColorHint(Color.TRANSPARENT);
        listView.setOnPositionChangedListener(this);
    }

    @Override
    public void onPositionChanged(SweepPanelListView listView, int position, View scrollBarPanel) {
        ((TextView) scrollBarPanel).setText("positon:" + position);
    }

    class DummyAdapter extends BaseAdapter {

        int numCount = 100;

        @Override
        public int getCount() {
            return numCount;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_item, null);
            TextView textView = (TextView) convertView.findViewById(R.id.text);
            textView.setText(position + "");
            return convertView;
        }
    }
}
