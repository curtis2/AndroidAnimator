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
 *  Bezier曲线让绘制的线更圆滑
 */

public class DrawPadView extends View {

    private Path mPath;
    private Paint mPaint;

    private  float mx;
    private  float my;
    public DrawPadView(Context context) {
        super(context);
        init();
    }

    public DrawPadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawPadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(16);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        mPath=new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
     switch (event.getAction()){
         case MotionEvent.ACTION_DOWN:
             mPath.reset();
             mx=event.getX();
             my=event.getY();
             mPath.moveTo(mx,my);
             break;
         case MotionEvent.ACTION_MOVE:
             float x=event.getX();
             float y=event.getY();
             float cx=(x+mx)/2;
             float cy=(y+my)/2;
//           mPath.lineTo(x,y);
             mPath.quadTo(mx,my,cx,cy);
             mx=x;
             my=y;
             break;
     }
         invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(mPath,mPaint);
    }
}
