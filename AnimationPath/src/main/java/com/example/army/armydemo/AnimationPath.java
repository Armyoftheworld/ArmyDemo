package com.example.army.armydemo;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/8/27
 * @description
 */
public class AnimationPath {
    private ArrayList<PointPath> pointPaths = new ArrayList<>(1000);

    public void moveTo(int mx, int my) {
        pointPaths.add(PointPath.moveTo(mx, my));
    }

    public void lineTo(int mx, int my) {
        pointPaths.add(PointPath.lineTo(mx, my));
    }

    public void cubicTo(int mx, int my, int cx0, int cy0, int cx1, int cy1) {
        pointPaths.add(PointPath.cubicTo(mx, my, cx0, cy0, cx1, cy1));
    }

    public Collection<PointPath> getPointPaths() {
        return pointPaths;
    }
}
