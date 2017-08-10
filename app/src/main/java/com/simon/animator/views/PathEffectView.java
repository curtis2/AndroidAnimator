package com.simon.animator.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * auther: elliott zhang
 * Emaill:18292967668@163.com
 */

public class PathEffectView extends View {


    private Path mPath;
    private Paint mPaint;

    private float mLength;
    private float mAnimValue;

    private PathEffect mEffect;
    private PathMeasure mPathMeasure;

    public PathEffectView(Context context) {
        super(context);
        init();
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathEffectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);

        mPath=new Path();

        mPath.moveTo(100,100);
        mPath.lineTo(100,400);
        mPath.lineTo(500,200);
        mPath.close();

        //初始化PathMeasure
        mPathMeasure=new PathMeasure();
        mPathMeasure.setPath(mPath,true);
        mLength=mPathMeasure.getLength();

        ValueAnimator valueanimator=ValueAnimator.ofFloat(1,0);
        valueanimator.setDuration(3000);
        valueanimator.setRepeatCount(ValueAnimator.INFINITE);
        valueanimator.setInterpolator(new LinearInterpolator());
        valueanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimValue= (float) animation.getAnimatedValue();
                //
                mEffect= new DashPathEffect(new float[]{mLength, mLength},mLength*mAnimValue);
                mPaint.setPathEffect(mEffect);
                invalidate();
            }
        });

        valueanimator.start();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
    }



}
