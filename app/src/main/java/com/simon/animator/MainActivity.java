package com.simon.animator;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.administrator.viewexplosion.ExplosionField;
import com.example.administrator.viewexplosion.factory.FallingParticleFactory;

import java.util.Random;


public class MainActivity extends AppCompatActivity {

    TextView textView;
    Button button;
    MyAnimView myAnimView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashHandleUtil.getmInstance().init(this,"mobileteacher_helper");
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.hello_animator);
        button= (Button) findViewById(R.id.button);
        myAnimView= (MyAnimView) findViewById(R.id.myAnimView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   explosionFieldTest();
//                   shakeAnimatorTest();
//                 animatorTestWithGone();
//                 animatorTest();
//                 evaluatorTest();
//                 evaluatorValueAnimatorTest();
//                 evaluatorObjectAnimatorTest();
//                 evaluatortAnimatorSetTest();
//                interpolatorAnimatorSetTest();
//                 viewPropertyAnimatorTest();
            }
        });
    }

    private ExplosionField mExplosionField;
    /**
     * 粒子动画效果
     */
    private void explosionFieldTest() {
        //ValueAnimator对值进行了一个平滑的动画过渡，
//        textView.setVisibility(View.VISIBLE);
        ExplosionField explosionField = new ExplosionField(this,new FallingParticleFactory());
        explosionField.explode(textView);
    }


    /**
     * 震动动画
     */
    private void shakeAnimatorTest() {
        textView.setVisibility(View.VISIBLE);
        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(150);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            Random random = new Random();
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                textView.setTranslationX((random.nextFloat() - 0.5f) * textView.getWidth() * 0.05f);
                textView.setTranslationY((random.nextFloat() - 0.5f) * textView.getHeight() * 0.05f);
            }
        });
        animator.start();
    }


    /**
     * view的从无到有的显示动画
     */
    private void animatorTestWithGone() {
        //ValueAnimator对值进行了一个平滑的动画过渡，
        textView.setVisibility(View.VISIBLE);
        //这里修改的时候textView的alpha属性，内部应该是通过在一个平衡过渡的时间段调用 View的setAlpha方法去不停通过视图重新绘制
        ObjectAnimator objectAnimatorX=ObjectAnimator.ofFloat(textView,"scaleX",0.4f,1f);
        ObjectAnimator objectAnimatorY=ObjectAnimator.ofFloat(textView,"scaleY",0.4f,1f);
        ObjectAnimator alphaAnimator=ObjectAnimator.ofFloat(textView,"alpha",0f,1f);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.play(objectAnimatorX).with(objectAnimatorY).with(alphaAnimator);
        animatorSet.start();
    }


    private void animatorTest() {
        //ValueAnimator对值进行了一个平滑的动画过渡，
        ValueAnimator valueAnimator=ValueAnimator.ofFloat(0f,1f);
        valueAnimator.setDuration(400);
        valueAnimator.start();
        valueAnimator.setRepeatCount(0);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
               Log.i("value=",animation.getAnimatedValue()+ "");
            }
        });

        //ObjectAnimator 对任何对象的任何属性做操作(其实就是动态的去修改该对象对象的属性)
        //这里修改的时候textView的alpha属性，内部应该是通过在一个平衡过渡的时间段调用 View的setAlpha方法去不停通过视图重新绘制
        ObjectAnimator objectAnimator=ObjectAnimator.ofFloat(textView,"alpha",0f,1f,0f);
        objectAnimator.setDuration(400);
//      objectAnimator.start();
        objectAnimator.setRepeatCount(-1);
        objectAnimator.setRepeatMode(ValueAnimator.RESTART);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i("value=",animation.getAnimatedValue()+ "");
            }
        });

       //组合动画AnimatorSet
/*      after(Animator anim_left)   将现有动画插入到传入的动画之后执行
        after(long delay)   将现有动画延迟指定毫秒后执行
        before(Animator anim_left)   将现有动画插入到传入的动画之前执行
        with(Animator anim_left)   将现有动画和传入的动画同时执行
        */
        ObjectAnimator translationAnimator=ObjectAnimator.ofFloat(textView,"translationY",400f,0f);//当前的translationY是基于自身当前位置的
        ObjectAnimator rotationAnimator=ObjectAnimator.ofFloat(textView,"rotation",0f,360f);
        ObjectAnimator alphaAnimator=ObjectAnimator.ofFloat(textView,"alpha",0f,1f);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(2000);
        //这个动画的执行顺序 translationAnimator和alphaAnimator同时，然后执行rotationAnimator
        animatorSet.play(translationAnimator).before(rotationAnimator).with(alphaAnimator);
        animatorSet.start();

        //动画监听器AnimatorListener/ AnimatorListener的适配器类AnimatorListenerAdapter
        ObjectAnimator animator=ObjectAnimator.ofFloat(textView,"translationY",400f,0f);//当前的translationY是基于自身当前位置的
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
                super.onAnimationCancel(animation);
            }
        });
    }

    private void evaluatorTest() {
        Point point1=new Point(0,0);
        Point point2=new Point(300,300);
        ValueAnimator animation=ValueAnimator.ofObject(new PointEvaluator(),point1,point2);
        animation.setDuration(500);
        animation.start();
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Log.i("PointValueX=",((Point)animation.getAnimatedValue()).getX()+ "");
            }
        });
    }


    private void evaluatorValueAnimatorTest() {
        //自定义evaluator，计算动画过程中的过渡值，然后在onAnimationUpdate去动态刷新视图
        Point point1=new Point(MyAnimView.REDIUS,MyAnimView.REDIUS);
        Point point2=new Point(myAnimView.getWidth()-MyAnimView.REDIUS,myAnimView.getHeight()-MyAnimView.REDIUS);
        ValueAnimator animation=ValueAnimator.ofObject(new PointEvaluator(),point1,point2);
        animation.setDuration(1000);
        animation.start();
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point mCurrentPoint=((Point)animation.getAnimatedValue());
                myAnimView.setCurrentPoint(mCurrentPoint);
                myAnimView.postInvalidate();
                Log.i("PointValueY=",((Point)animation.getAnimatedValue()).getY()+ "");
            }
        });
    }


    private void evaluatorObjectAnimatorTest() {
        // 自定义view的ObjectAnimator，实现自己的Evaluator，并且传入自己的属性，和ValueAnimator的区别就是 不需要在onAnimationUpdate重新绘制，会自动调用对象的set属性方法进行重绘
        ObjectAnimator colorAnimator=ObjectAnimator.ofObject(myAnimView,"color",new ColorEvaluator(),"#0000FF", "#FF0000");
        colorAnimator.setDuration(10000);
        colorAnimator.start();
    }

    private void evaluatortAnimatorSetTest() {
        //自定义evaluator，计算动画过程中的过渡值，然后在onAnimationUpdate去动态刷新视图
        Point point1=new Point(MyAnimView.REDIUS,MyAnimView.REDIUS);
        Point point2=new Point(myAnimView.getWidth()-MyAnimView.REDIUS,myAnimView.getHeight()-MyAnimView.REDIUS);
        ValueAnimator locationAnimation=ValueAnimator.ofObject(new PointEvaluator(),point1,point2);
        locationAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point mCurrentPoint=((Point)animation.getAnimatedValue());
                myAnimView.setCurrentPoint(mCurrentPoint);
                Log.i("PointValueY=",((Point)animation.getAnimatedValue()).getY()+ "");
            }
        });
        ObjectAnimator colorAnimator=ObjectAnimator.ofObject(myAnimView,"color",new ColorEvaluator(),"#0000FF", "#FF0000");

        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.setDuration(1000);
        animatorSet.play(locationAnimation).with(colorAnimator);
        animatorSet.start();
    }

     //自定义补间器
    private void interpolatorAnimatorSetTest() {
        //自定义evaluator，计算动画过程中的过渡值，然后在onAnimationUpdate去动态刷新视图
        Point point1=new Point(myAnimView.getWidth()/2,MyAnimView.REDIUS);
        Point point2=new Point(myAnimView.getWidth()/2,myAnimView.getHeight()-MyAnimView.REDIUS);
        ValueAnimator interpolatorAnimation=ValueAnimator.ofObject(new PointEvaluator(),point1,point2);
        interpolatorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Point mCurrentPoint=((Point)animation.getAnimatedValue());
                myAnimView.setCurrentPoint(mCurrentPoint);
                myAnimView.postInvalidate();
                Log.i("PointValueY=",((Point)animation.getAnimatedValue()).getY()+ "");
            }
        });
        interpolatorAnimation.setDuration(3000);
//      interpolatorAnimation.setInterpolator(new BounceInterpolator());
//      interpolatorAnimation.setInterpolator(new AnticipateInterpolator());
//      interpolatorAnimation.setInterpolator(new AnticipateOvershootInterpolator());
//      interpolatorAnimation.setInterpolator(new LinearInterpolator());
//      interpolatorAnimation.setInterpolator(new CycleInterpolator(myAnimView.getWidth()/4));
        interpolatorAnimation.setInterpolator(new DecelerateAccelerateInterpolator());
        interpolatorAnimation.start();
    }

    /**
     * 通过ViewPropertyAnimator的给view设置动画，调用方式简单，基本上能够覆盖补间动画的内容。但是有缺陷，不能设置重复模式
     */
    public  void viewPropertyAnimatorTest(){
        textView.animate().setDuration(500).alphaBy(0).start();
    }

}
