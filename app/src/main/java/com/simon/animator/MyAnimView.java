package com.simon.animator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * auther: elliott zhang
 * Emaill:18292967668@163.com
 */

public class MyAnimView extends View {
    public static final float REDIUS=50;
    private Point currentPoint;
    private Paint mPaint;
    private String color;

    public MyAnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint =new Paint();
        mPaint.setColor(Color.BLUE);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(currentPoint==null){
            currentPoint=new Point(REDIUS,REDIUS);
            drawCircle(canvas);
        }else{
            drawCircle(canvas);
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
        mPaint.setColor(Color.parseColor(color));
        invalidate();
    }

    public void setCurrentPoint(Point currentPoint) {
        this.currentPoint = currentPoint;
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(currentPoint.getX(),currentPoint.getY(),REDIUS, mPaint);
    }

}
