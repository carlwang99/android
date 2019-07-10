package android.wecrowds.com.exercise3;

import android.animation.AnimatorSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class Exer3Activty extends AppCompatActivity {
    private Button showAlphaBtn,showScaleBtn,showBtn;
    private ImageView mImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exer3_activty);

        mImage = findViewById(R.id.im);
        showAlphaBtn = findViewById(R.id.showAlphaBtn);
        showScaleBtn = findViewById(R.id.showScaleBtn);
        showBtn = findViewById(R.id.showBtn);

        showAlphaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Animation loadAnimation;
                loadAnimation = AnimationUtils.loadAnimation(Exer3Activty.this,R.anim.alpha);
                mImage.startAnimation(loadAnimation);
            }
        });

        showScaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation loadAnimation;
                loadAnimation = AnimationUtils.loadAnimation(Exer3Activty.this,R.anim.scale);
                mImage.startAnimation(loadAnimation);
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlphaAnimation alphaAnimation = new AlphaAnimation(0,1);
                ScaleAnimation scaleAnimation = new ScaleAnimation(0f,1f,0f,1f,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
                AnimationSet animationSet = new AnimationSet(true);
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(scaleAnimation);
                animationSet.setDuration(1000);
                mImage.startAnimation(animationSet);
            }
        });
    }
}
