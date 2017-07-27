package com.simon.animator;

import android.animation.TypeEvaluator;

/**
 * auther: elliott zhang
 * Emaill:18292967668@163.com
 */

public class PointEvaluator implements TypeEvaluator<Point> {
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        float x=startValue.getX()+fraction*(endValue.getX()-startValue.getX());
        float y=startValue.getY()+fraction*(endValue.getY()-startValue.getY());
        Point point=new Point(x,y);
        return point;
    }


}
