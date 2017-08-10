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
 *  2阶曲线的使用
 *
 */

public class SecondBezierView extends View {

    private float mPointStartX;
    private float mPointStartY;

    private float mPointEndX;
    private float mPointEndY;

    private float mFlagPointEndX;
    private float mFlagPointEndY;


    private Path mPath;
    private Paint mPaint;
    private Paint mFlagPaint;

    public SecondBezierView(Context context) {
        super(context);
        init();
    }

    public SecondBezierView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SecondBezierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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

        mFlagPointEndX= w/2;
        mFlagPointEndY=h/2-300;

        mPath=new Path();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();

        canvas.drawText("起点",mPointStartX,mPointStartY, mFlagPaint);
        canvas.drawText("终点",mPointEndX,mPointEndY, mFlagPaint);
        canvas.drawText("控制点",mFlagPointEndX,mFlagPointEndY, mFlagPaint);

        canvas.drawLine(mPointStartX,mPointStartY, mFlagPointEndX,mFlagPointEndY,mFlagPaint);
        canvas.drawLine(mPointEndX,mPointEndY, mFlagPointEndX,mFlagPointEndY,mFlagPaint);

        mPath.moveTo(mPointStartX,mPointStartY);

        mPath.quadTo(mFlagPointEndX,mFlagPointEndY,mPointEndX,mPointEndY);
        canvas.drawPath(mPath,mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mFlagPointEndX=event.getX();
                mFlagPointEndY=event.getY();
                invalidate();
            break;
        }
        return true;
    }
}
