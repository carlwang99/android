package com.bytedance.androidcamp.network.dou;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;

public class CoverActivity extends AppCompatActivity {
    private TextView skip_btn;
    private Handler mHandler = new Handler();
    private int ticks;
    private Runnable tickRunnable = new Runnable() {
        public void run() {
            ticks = ticks - 1;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(ticks == 0){
                        //跳转
                        finish();
                        Intent intent = new Intent(CoverActivity.this, IndexActivity.class);
                        startActivity(intent);
                    }
                    else{
                        //更改Button的数据
                        skip_btn.setText("跳过 | "+ticks+"s");
                        mHandler.postDelayed(tickRunnable, 1000);
                    }
                }
            });
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cover);
        skip_btn = findViewById(R.id.skip_btn);
        skip_btn.bringToFront();
        ticks = 5;
        addImage(R.drawable.cover);
        mHandler.post(tickRunnable);
        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(tickRunnable);
                //跳转
                finish();
                Intent intent = new Intent(CoverActivity.this, IndexActivity.class);
                startActivity(intent);
            }
        });
    }

    private void addImage(int resId) {
        ImageView imageView = findViewById(R.id.image);
        Glide.with(this).load(resId).into(imageView);
    }
}
