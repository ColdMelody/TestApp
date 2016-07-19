package com.demos.testapp.activity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.demos.testapp.R;

public class AnimationActivity extends AppCompatActivity {
    private Button btnAnim;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        btnAnim = (Button) findViewById(R.id.start_anim);
        tv = (TextView) findViewById(R.id.show);
        btnAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                combine();
            }
        });
    }

    private void alpha() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(tv, "alpha", 1f, 0f, 1f);
        animator.setDuration(3000);
        animator.start();
    }
    private void rotation(){
        ObjectAnimator animator=ObjectAnimator.ofFloat(tv,"rotation",0f,360f);
        animator.setDuration(3000);
        animator.start();
    }
    private void translation(){
        float tranX=tv.getTranslationX();
        ObjectAnimator animator=ObjectAnimator.ofFloat(tv,"translationX",tranX,-500f,tranX);
        animator.setDuration(3000);
        animator.start();
    }
    private void scaleY(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(tv, "scaleY", 1f, 3f, 1f);
        animator.setDuration(5000);
        animator.start();
    }
    private void combine(){
        ObjectAnimator moveIn = ObjectAnimator.ofFloat(tv, "translationX", -500f, 0f);
        ObjectAnimator rotate = ObjectAnimator.ofFloat(tv, "rotation", 0f, 360f);
        ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(tv, "alpha", 1f, 0f, 1f);
        AnimatorSet animSet = new AnimatorSet();
        animSet.play(rotate).with(fadeInOut).after(moveIn);
        animSet.setDuration(5000);
        animSet.start();
    }

}
