package com.simon.animator.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.simon.animator.BezierPathEvaluator;

/**
 * auther: elliott zhang
 * Emaill:18292967668@163.com
 * 使用bezier曲线模拟物体的运动轨迹动画
 *
 */

public class BezierPathView extends View implements View.OnClickListener {

    private float mPointStartX;
    private float mPointStartY;

    private float mPointEndX;
    private float mPointEndY;

    private float mFlagPointEndX;
    private float mFlagPointEndY;


    private float mMovePointEndX;
    private float mMovePointEndY;
    private Path mPath;
    private Paint mPathPaint;
    private Paint mPointPaint;
    private Paint mMovePointPaint;

    private float mRadius=10;

    private ValueAnimator mObjectAnimator;

    public BezierPathView(Context context) {
        super(context);
        init();
    }

    public BezierPathView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPointStartX=w/4;
        mPointStartY=h/4;

        mFlagPointEndX=w*5/6;
        mFlagPointEndY=h/2;

        mPointEndX=w*7/10;
        mPointEndY=h*3/4;

        mMovePointEndX=mPointStartX;
        mMovePointEndY=mPointStartY;

    }

    private void init() {
        mPath=new Path();
        mPathPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mPathPaint.setStrokeWidth(5);
        mPathPaint.setStyle(Paint.Style.STROKE);
        mPathPaint.setColor(Color.GRAY);

        mPointPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mPointPaint.setColor(Color.BLACK);
        mPointPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mMovePointPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mMovePointPaint.setColor(Color.BLUE);
        mMovePointPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        setOnClickListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.reset();
        mPath.moveTo(mPointStartX,mPointStartY);
        mPath.quadTo(mFlagPointEndX,mFlagPointEndY,mPointEndX,mPointEndY);
        canvas.drawPath(mPath,mPathPaint);

        canvas.drawCircle(mPointStartX,mPointStartY,mRadius,mPointPaint);
        canvas.drawCircle(mPointEndX,mPointEndY,mRadius,mPointPaint);
        canvas.drawCircle(mMovePointEndX,mMovePointEndY,mRadius,mMovePointPaint);
    }

    @Override
    public void onClick(View v) {
        BezierPathEvaluator evaluator=new BezierPathEvaluator(new PointF(mFlagPointEndX,mFlagPointEndY));
        mObjectAnimator= ValueAnimator.ofObject(evaluator,new PointF(mPointStartX,mPointStartY),new PointF(mPointEndX,mPointEndY));
        mObjectAnimator.setDuration(1000);
        mObjectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mObjectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               mMovePointEndX= ((PointF)animation.getAnimatedValue()).x;
               mMovePointEndY= ((PointF)animation.getAnimatedValue()).y;
               invalidate();
            }
        });
        mObjectAnimator.start();
    }
}
