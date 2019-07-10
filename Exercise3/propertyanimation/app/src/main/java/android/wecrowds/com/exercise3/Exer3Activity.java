package android.wecrowds.com.exercise3;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;

public class Exer3Activity extends AppCompatActivity {
    private ImageView mImage;
    private AnimatorSet animatorSet1,animatorSet2,animatorSet3;
    private Button mShowBtn, mShowScaleBtn, mShowAlphaBtn;
    private Button stopAnimation1,stopAnimation2,stopAnimation3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer3);

        mImage = findViewById(R.id.im);
        mShowBtn = findViewById(R.id.showBtn);
        mShowScaleBtn = findViewById(R.id.showScaleBtn);
        mShowAlphaBtn = findViewById(R.id.showAlphaBtn);
        stopAnimation1 = findViewById(R.id.stop1);
        stopAnimation2 = findViewById(R.id.stop2);
        stopAnimation3 = findViewById(R.id.stop3);

        InitAnimator();
        stopAnimation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatorSet1.end();
            }
        });
        stopAnimation2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatorSet2.end();
            }
        });
        stopAnimation3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animatorSet3.end();
            }
        });
        mShowScaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!animatorSet2.isStarted()){
                    animatorSet2.start();
                }
                else if(animatorSet2.isPaused()){
                    animatorSet2.resume();
                }
                else{
                    animatorSet2.pause();
                }
            }
        });

        mShowAlphaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!animatorSet1.isStarted()){
                    animatorSet1.start();
                }
                else if(animatorSet1.isPaused()){
                    animatorSet1.resume();
                }
                else{
                    animatorSet1.pause();
                }
            }
        });

        mShowBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!animatorSet3.isStarted()){
                    animatorSet3.start();
                }
                else if(animatorSet3.isPaused()){
                    animatorSet3.resume();
                }
                else{
                    animatorSet3.pause();
                }
            }
        });
    }

    private void InitAnimator() {
        ObjectAnimator mScaleXanimator = ObjectAnimator.ofFloat(mImage,"scaleX",0f,1f);
        ObjectAnimator mScaleYanimator = ObjectAnimator.ofFloat(mImage,"scaleY",0f,1f);
        ObjectAnimator mAlphaAnimator = ObjectAnimator.ofFloat(mImage,"alpha",0,1);
        mScaleXanimator.setRepeatCount(ValueAnimator.INFINITE);
        mScaleYanimator.setRepeatCount(ValueAnimator.INFINITE);
        mScaleXanimator.setInterpolator(new LinearInterpolator());
        mScaleYanimator.setInterpolator(new LinearInterpolator());
        mScaleXanimator.setDuration(2000);
        mScaleYanimator.setDuration(2000);
        mScaleXanimator.setRepeatMode(ValueAnimator.REVERSE);
        mScaleYanimator.setRepeatMode(ValueAnimator.REVERSE);
        mAlphaAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mAlphaAnimator.setInterpolator(new LinearInterpolator());
        mAlphaAnimator.setDuration(2000);
        mAlphaAnimator.setRepeatMode(ValueAnimator.REVERSE);
        animatorSet1 = new AnimatorSet();
        animatorSet2 = new AnimatorSet();
        animatorSet3 = new AnimatorSet();
        animatorSet1.play(mAlphaAnimator);
        animatorSet2.playTogether(mScaleXanimator,mScaleYanimator);
        animatorSet3.playTogether(mScaleXanimator,mScaleYanimator,mAlphaAnimator);
    }
}
