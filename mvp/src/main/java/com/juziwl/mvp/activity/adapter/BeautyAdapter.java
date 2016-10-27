package com.juziwl.mvp.activity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.juziwl.mvp.R;
import com.juziwl.mvp.model.Beauty;

import java.util.ArrayList;

/**
 * @author dai
 * @version V_5.0.0
 * @date 2016/10/27
 * @description 应用程序Activity的模板类
 */

public class BeautyAdapter extends BaseAdapter {
    private ArrayList<Beauty> beauties;
    private Context context;

    public BeautyAdapter(ArrayList<Beauty> beauties, Context context) {
        this.beauties = beauties;
        this.context = context;
    }

    @Override
    public int getCount() {
        return beauties.size();
    }

    @Override
    public Beauty getItem(int position) {
        return beauties.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_listview_item, null);
            holder = new ViewHolder();
            holder.desc = (TextView) convertView.findViewById(R.id.desc);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Beauty beauty = getItem(position);
        holder.desc.setText(beauty.desc);
        holder.image.setImageResource(beauty.resId);
        holder.name.setText(beauty.name);
        return convertView;
    }

    class ViewHolder {
        TextView name, desc;
        ImageView image;
    }
}
