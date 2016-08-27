package com.example.army.armydemo;

/**
 * @author Army
 * @version V_1.0.0
 * @date 2016/8/27
 * @description
 */
public class PointPath {
    public int mOperation;
    public static final int LINE = 0;
    public static final int MOVE = 1;
    public static final int CUBIC = 2;

    public float mx, my, cx0, cy0, cx1, cy1;

    private PointPath(int mOperation, float mx, float my) {
        this.mOperation = mOperation;
        this.mx = mx;
        this.my = my;
    }

    private PointPath(float mx, float my, float cx0, float cy0, float cx1, float cy1) {
        mOperation = CUBIC;
        this.mx = mx;
        this.my = my;
        this.cx0 = cx0;
        this.cy0 = cy0;
        this.cx1 = cx1;
        this.cy1 = cy1;
    }

    public static PointPath moveTo(float mx, float my) {
        return new PointPath(MOVE, mx, my);
    }

    public static PointPath lineTo(float mx, float my) {
        return new PointPath(LINE, mx, my);
    }

    public static PointPath cubicTo(float mx, float my, float cx0, float cy0, float cx1, float cy1) {
        return new PointPath(mx, my, cx0, cy0, cx1, cy1);
    }

    @Override
    public String toString() {
        return "PointPath{" +
                "mOperation=" + mOperation +
                ", mx=" + mx +
                ", my=" + my +
                ", cx0=" + cx0 +
                ", cy0=" + cy0 +
                ", cx1=" + cx1 +
                ", cy1=" + cy1 +
                '}';
    }
}

