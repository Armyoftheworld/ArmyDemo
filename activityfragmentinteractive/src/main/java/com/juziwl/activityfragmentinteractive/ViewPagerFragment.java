package com.juziwl.activityfragmentinteractive;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/9/27
 * @description 应用程序Activity的模板类
 */
public class ViewPagerFragment extends LazyLoadBaseFragment implements OnActivityUseFragmentMethodListener {

    private TextView text;
    private Button button;
    private int position = -1;
    private boolean isPrepared = false;
    private boolean mHasLoadedOnce = false;
    private View view;
    private OnActivityUseFragmentMethodListener onActivityUseFragmentMethodListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt("position", -1);
        System.out.println("onCreate   position == " + position);
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce)
            return;
        System.out.println("lazyLoad position == " + position);
        mHasLoadedOnce = true;
    }

    @Override
    public void stateUpdate() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.layout_myfragment, container, false);
            text = (TextView) view.findViewById(R.id.text);
            button = (Button) view.findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onButtonClick();
                }
            });
            isPrepared = true;
            lazyLoad();
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        return view;
    }

    public void onButtonClick() {
        if (onActivityUseFragmentMethodListener != null) {
            onActivityUseFragmentMethodListener.onActivityUseFragmentMethod("position == " + position + "\ttext == " + (Math.random() * 1000 + 1000));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnActivityUseFragmentMethodListener) {
            onActivityUseFragmentMethodListener = (OnActivityUseFragmentMethodListener) context;
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            if (activity instanceof OnActivityUseFragmentMethodListener) {
                onActivityUseFragmentMethodListener = (OnActivityUseFragmentMethodListener) activity;
            }
        }
    }

    @Override
    public void onActivityUseFragmentMethod(String content) {
        if(!mHasLoadedOnce)
            return;
        if (text != null) {
            text.setText(content);
        }
        System.out.println("ViewPagerFragment   text == " + content);
    }
}
