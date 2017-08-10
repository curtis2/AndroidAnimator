package com.simon.animator.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * auther: elliott zhang
 * Emaill:18292967668@163.com
 */

public class PathTracingView extends View {

    private Path mDst;
    private Path mPath;
    private Paint mPaint;

    private float mLength;
    private float mAnimValue;

    private PathMeasure mPathMeasure;

    public PathTracingView(Context context) {
        super(context);
        init();
    }

    public PathTracingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathTracingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);


        mDst=new Path();
        mPath=new Path();

        mPath.addCircle(400,400,100, Path.Direction.CW);

        //初始化PathMeasure
        mPathMeasure=new PathMeasure();
        mPathMeasure.setPath(mPath,false);
        mLength=mPathMeasure.getLength();


        ValueAnimator valueanimator=ValueAnimator.ofFloat(0,1);
        valueanimator.setDuration(1000);
        valueanimator.setRepeatCount(ValueAnimator.INFINITE);
        valueanimator.setInterpolator(new LinearInterpolator());
        valueanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimValue= (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        valueanimator.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDst.reset();
        mDst.lineTo(0,0);

        //计算截取的距离
        float stop=mAnimValue*mLength;
        float start = (float) (stop - ((0.5 - Math.abs(mAnimValue - 0.5)) * mLength));
        //将截取的距离赋值给mDst

        //PathMeasure的getSegment方法可以从一个路径中截取一段路径
        mPathMeasure.getSegment(start, stop, mDst, true);

        canvas.drawPath(mDst,mPaint);
    }
}
