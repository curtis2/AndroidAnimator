package com.simon.animator;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * auther: elliott zhang
 * Emaill:18292967668@163.com
 */

public class BezierPathEvaluator implements TypeEvaluator<PointF> {
    //控制点
    private PointF pointflagF;

    public BezierPathEvaluator(PointF pointF) {
        this.pointflagF = pointF;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        return BezierUtils.CalculateBezierPointForQuadratic(fraction,startValue,pointflagF, endValue);
    }

}
