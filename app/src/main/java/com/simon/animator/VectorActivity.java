package com.simon.animator;

import android.annotation.TargetApi;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;

public class VectorActivity extends AppCompatActivity {

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vector);
    }


    public  void anim(View view){
        ImageView imageView= (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        if(drawable instanceof Animatable){
            ((Animatable)drawable).start();
        }
    }


    public  void anim_color(View view){
        ImageView imageView= (ImageView) view;
        Drawable drawable = imageView.getDrawable();
        if(drawable instanceof Animatable){
            ((Animatable)drawable).start();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void animL(View view) {
        ImageView imageView = (ImageView) view;
        AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) getDrawable(R.drawable.anim_fivestar_vr);
        imageView.setImageDrawable(drawable);
        if (drawable != null) {
            drawable.start();
        }
    }

}
