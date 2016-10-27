package com.juziwl.mvp.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.juziwl.mvp.R;
import com.juziwl.mvp.activity.adapter.BeautyAdapter;
import com.juziwl.mvp.model.Beauty;
import com.juziwl.mvp.presenter.impl.IBeautyPresenter;
import com.juziwl.mvp.view.interfaces.IBeautyView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity<IBeautyView, IBeautyPresenter> implements IBeautyView {
    private ListView listview;
    private BeautyAdapter adapter;
    private ArrayList<Beauty> beauties = new ArrayList<>();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById();
        initView();
        presenter.initData();
    }

    @Override
    protected void initView() {
        adapter = new BeautyAdapter(beauties, this);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(MainActivity.this, SecondActivity.class));
            }
        });
    }

    @Override
    protected void findViewById() {
        listview = (ListView) findViewById(R.id.listview);
    }

    @Override
    public void showData(ArrayList<Beauty> data) {
        beauties.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showDialog() {
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(android.R.style.Widget_ProgressBar_Large);
        }
        dialog.show();
    }

    @Override
    public void dismissDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    @Override
    public void handleError(Throwable e, int flag) {

    }

    @Override
    protected IBeautyPresenter createPresenter() {
        return new IBeautyPresenter();
    }
}
