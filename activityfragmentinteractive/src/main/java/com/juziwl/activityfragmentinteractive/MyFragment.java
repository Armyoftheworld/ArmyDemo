package com.juziwl.activityfragmentinteractive;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/9/27
 * @description 应用程序Activity的模板类
 */
public class MyFragment extends Fragment implements OnActivityUseFragmentMethodListener {
    private TextView text;
    private OnActivityUseFragmentMethodListener onActivityUseFragmentMethodListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_myfragment, container, false);
        text = (TextView) view.findViewById(R.id.text);
        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonClick(v);
            }
        });
        return view;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (context instanceof OnActivityUseFragmentMethodListener) {
                onActivityUseFragmentMethodListener = (OnActivityUseFragmentMethodListener) context;
            }
        }
        super.onAttach(context);
    }

    @TargetApi(23)
    @Override
    public void onAttach(Context context) {
        if (context instanceof OnActivityUseFragmentMethodListener) {
            onActivityUseFragmentMethodListener = (OnActivityUseFragmentMethodListener) context;
        }
        super.onAttach(context);
    }

    @Override
    public void onActivityUseFragmentMethod(String content) {
        text.setText("receive data:" + content);
    }

    public void onButtonClick(View v) {
        if (onActivityUseFragmentMethodListener != null) {
            onActivityUseFragmentMethodListener.onActivityUseFragmentMethod((Math.random() * 1000 + 1000) + "");
        }
    }
}
