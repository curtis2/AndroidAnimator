package com.simon.animator.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * auther: elliott zhang
 * Emaill:18292967668@163.com
 *
 *  3阶曲线的使用
 */

public class ThirdBezierView extends View {

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

    public ThirdBezierView(Context context) {
        super(context);
        init();
    }

    public ThirdBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ThirdBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        mFlagPointOneX = w/2-100;
        mFlagPointOneY =h/2-300;

        mFlagPointTwoX = w/2+100;
        mFlagPointTwoY =h/2-300;

        mPath=new Path();
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

    @Override
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
    }
}
