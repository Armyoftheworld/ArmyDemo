package com.army.bouncemenu;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/10/29
 * @description
 */

public class BounceMenu {

    private RecyclerView recyclerView;
    private View view;
    private BounceView bounceView;
    private ViewGroup parent;
    private RecyclerView.Adapter adapter;

    public BounceMenu(Activity activity, int layoutId, View outview, final RecyclerView.Adapter adapter) {
        view = LayoutInflater.from(activity).inflate(layoutId, null, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        bounceView = (BounceView) view.findViewById(R.id.bounce);
        parent = (ViewGroup) findRootView(outview);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        this.adapter = adapter;
        bounceView.setBounceAnimationListener(new BounceView.BounceAnimationListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onEnd() {

            }

            @Override
            public void onContentShow() {
                recyclerView.setAdapter(adapter);
                recyclerView.scheduleLayoutAnimation();
            }
        });
    }

    public static BounceMenu make(Activity activity, int layoutId, View outview, RecyclerView.Adapter adapter) {
        BounceMenu bounceMenu = new BounceMenu(activity, layoutId, outview, adapter);
        return bounceMenu;
    }

    public BounceMenu show() {
        if (view.getParent() != null) {
            parent.removeView(view);
        }
        parent.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        bounceView.show();
        return this;
    }

    public void dismiss() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(view, "translationY", 0, view.getHeight());
        animator.setDuration(400);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                parent.removeView(view);
                view = null;
            }
        });
        animator.start();
    }

    public boolean isShow() {
        return view != null;
    }

    private View findRootView(View outView) {
        View parent = (View) outView.getParent();
        while (true) {
            if (parent == null)
                break;
            if (parent instanceof FrameLayout && parent.getId() == android.R.id.content) {
                break;
            } else {
                parent = parent.getParent() instanceof View ? (View) parent.getParent() : null;
            }
        }
        return parent;
    }
}
