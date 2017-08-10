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
import android.view.animation.BounceInterpolator;

/**
 * auther: elliott zhang
 * Emaill:18292967668@163.com
 *  Bezier曲线 实现路径动画的原理
 */

public class PathMorphingBezierView extends View  implements View.OnClickListener{
    private float mPointStartX;
    private float mPointStartY;
    private float mPointEndX;
    private float mPointEndY;

    private float mFlagPointOneX;
    private float mFlagPointOneY;

    private float mFlagPointTwoX;
    private float mFlagPointTwoY;

    private Path mPath;
    private Paint mPaint;
    private Paint mFlagPaint;

    private  boolean isSecondMove;

    ValueAnimator mValueAnimator;

    public PathMorphingBezierView(Context context) {
        super(context);
        init();
    }

    public PathMorphingBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PathMorphingBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(8);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);

        mFlagPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mFlagPaint.setStrokeWidth(4);
        mFlagPaint.setColor(Color.BLACK);
        mFlagPaint.setStyle(Paint.Style.FILL);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPointStartX=w*1/4;
        mPointStartY=h/2-200;

        mPointEndX=w*3/4;
        mPointEndY=h/2-200;

        mFlagPointOneX =mPointStartX;
        mFlagPointOneY =mPointStartY;

        mFlagPointTwoX =mPointEndX;
        mFlagPointTwoY =mFlagPointOneY;

        mPath=new Path();

        mValueAnimator = ValueAnimator.ofFloat(mPointStartY,h);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFlagPointOneY= (float) animation.getAnimatedValue();
                mFlagPointTwoY= (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.setDuration(1000);
        mValueAnimator.setInterpolator(new BounceInterpolator());
        setOnClickListener(this);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();

        canvas.drawText("起点",mPointStartX,mPointStartY, mFlagPaint);
        canvas.drawText("终点",mPointEndX,mPointEndY, mFlagPaint);
        canvas.drawText("控制点1", mFlagPointOneX, mFlagPointOneY, mFlagPaint);
        canvas.drawText("控制点2", mFlagPointTwoX, mFlagPointTwoY, mFlagPaint);

        canvas.drawLine(mPointStartX,mPointStartY, mFlagPointOneX, mFlagPointOneY,mFlagPaint);
        canvas.drawLine(mFlagPointOneX,mFlagPointOneY, mFlagPointTwoX, mFlagPointTwoY,mFlagPaint);
        canvas.drawLine(mFlagPointTwoX,mFlagPointTwoY, mPointEndX, mPointEndY,mFlagPaint);


        mPath.moveTo(mPointStartX,mPointStartY);

        mPath.cubicTo(mFlagPointOneX, mFlagPointOneY,mFlagPointTwoX,mFlagPointTwoY,mPointEndX,mPointEndY);
        canvas.drawPath(mPath,mPaint);

    }

/*  @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()&MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_POINTER_DOWN:
                isSecondMove=true;
                break;
            case MotionEvent.ACTION_POINTER_UP:
                isSecondMove=false;
                break;
            case MotionEvent.ACTION_MOVE:
                mFlagPointOneX =event.getX(0);
                mFlagPointOneY =event.getY(0);
                if(isSecondMove){
                    mFlagPointTwoX=event.getX(1);
                    mFlagPointTwoY=event.getY(1);
                }
                invalidate();
            break;
        }
        return true;
    }*/

    @Override
    public void onClick(View v) {
        mValueAnimator.start();
    }
}
