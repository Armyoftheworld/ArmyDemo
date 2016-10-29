package com.army.bouncemenu;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/10/29
 * @description
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {
    private int width;

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item, parent,false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.tv.setText("第" + (position + 1) + "个item");
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView tv;

        public MyHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.textView);
        }
    }
}
