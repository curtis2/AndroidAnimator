package com.simon.animator.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * auther: elliott zhang
 * Emaill:18292967668@163.com
 *  Bezier实现波浪效果
 */

public class WaveView extends View  implements View.OnClickListener{

    private Path mPath;
    private Paint mPaint;

    private int mWaveLength=400;
    private int mScreenWidth;
    private int mScreenHeight;
    private int mCenterY;
    private int mWaveCount;


    private float mOffsetX;
    ValueAnimator mValueAnimator;

    public WaveView(Context context) {
        super(context);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.BLUE);
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mScreenWidth=w;
        mScreenHeight=h;
        mCenterY=h/2;
        mWaveCount= (int) Math.round(mScreenWidth/mWaveLength+1.5);
        mPath=new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(-mWaveLength+mOffsetX,mCenterY);
        for (int i = 0; i <mWaveCount ; i++) {
            mPath.quadTo(-mWaveLength * 3 / 4+i*mWaveLength+mOffsetX,mCenterY-60,-mWaveLength/2+i*mWaveLength+mOffsetX,mCenterY);
            mPath.quadTo(-mWaveLength / 4+i*mWaveLength+mOffsetX,mCenterY+60,i*mWaveLength+mOffsetX,mCenterY);
        }
        mPath.lineTo(mScreenWidth,mScreenHeight);
        mPath.lineTo(0,mScreenHeight);
        mPath.close();
        canvas.drawPath(mPath,mPaint);
    }


    @Override
    public void onClick(View v) {
        mValueAnimator=ValueAnimator.ofFloat(0,mWaveLength);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mOffsetX= (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }
}
