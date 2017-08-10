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

public class PathPostTanView extends View implements View.OnClickListener{

    private Path mPath;
    private Paint mPaint;

    private float mCurrentValue;

    private float[] mPos;
    private float[] mTan;

    private PathMeasure mPathMeasure;
    private ValueAnimator valueanimator;
    public PathPostTanView(Context context) {
        super(context);
        init();
    }

    public PathPostTanView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathPostTanView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);


        mPath=new Path();

        mPath.addCircle(0,0,200, Path.Direction.CW);

        //初始化PathMeasure
        mPathMeasure=new PathMeasure();
        mPathMeasure.setPath(mPath,false);


        mPos = new float[2];
        mTan = new float[2];

        setOnClickListener(this);

        valueanimator=ValueAnimator.ofFloat(0,1);
        valueanimator.setDuration(1000);
        valueanimator.setRepeatCount(ValueAnimator.INFINITE);
        valueanimator.setInterpolator(new LinearInterpolator());
        valueanimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurrentValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPathMeasure.getPosTan(mCurrentValue * mPathMeasure.getLength(), mPos, mTan);
        float degree = (float) (Math.atan2(mTan[1], mTan[0]) * 180 / Math.PI);
        canvas.save();
        canvas.translate(400, 400);
        canvas.drawPath(mPath, mPaint);
        canvas.drawCircle(mPos[0], mPos[1], 10, mPaint);
        canvas.rotate(degree);
        canvas.drawLine(0, -200, 300, -200, mPaint);
        canvas.restore();
    }

    @Override
    public void onClick(View v) {
        valueanimator.start();
    }


}
