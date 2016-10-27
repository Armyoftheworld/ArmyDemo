package com.juziwl.mvp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.ListView;

import com.juziwl.mvp.R;
import com.juziwl.mvp.activity.adapter.BeautyAdapter;
import com.juziwl.mvp.model.Beauty;
import com.juziwl.mvp.presenter.impl.IBeautyPresenter2;
import com.juziwl.mvp.view.interfaces.IBeautyView2;

import java.util.ArrayList;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/27
 * @description 应用程序Activity的模板类
 */

public class SecondActivity extends BaseActivity<IBeautyView2, IBeautyPresenter2> implements IBeautyView2 {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ListView listView;
    private ArrayList<Beauty> beauties = new ArrayList<>();
    private BeautyAdapter adapter;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        findViewById();
        initView();
        presenter.initData();
    }

    @Override
    protected IBeautyPresenter2 createPresenter() {
        return new IBeautyPresenter2();
    }

    @Override
    protected void initView() {
        adapter = new BeautyAdapter(beauties, this);
        listView.setAdapter(adapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });
    }

    @Override
    protected void findViewById() {
        listView = (ListView) findViewById(R.id.listview);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
    }

    @Override
    public void onRefresh(ArrayList<Beauty> data) {
        beauties.addAll(0, data);
        adapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoading(ArrayList<Beauty> data) {

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
}
