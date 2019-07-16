package com.bytedance.androidcamp.network.dou;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class IndexActivity extends AppCompatActivity {
    private TextView[] tabItem = new TextView[4];
    private boolean loginState = false; //记录登录状态
    final int focusedColor = Color.parseColor("#ffffff");
    final int basicColor = Color.parseColor("#99999a");
    private int currentFocus = 0;

    //自定义按钮点击监听器，可接收tab按钮id
    class BtnOnclickListener implements View.OnClickListener{
        private int tabBtnId;
        public BtnOnclickListener(int id){
            tabBtnId = id;
        }
        @Override
        public void onClick(View v) {
            if(tabBtnId != 0 && loginState == false){
                Intent intent = new Intent(IndexActivity.this,LoginActivity.class);
                //播放动画
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(IndexActivity.this).toBundle());
                return;
            }
            if(tabBtnId == currentFocus){
                //播放刷新动画
            }
            else{
                tabItem[tabBtnId].setTextColor(focusedColor);
                tabItem[tabBtnId].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                tabItem[currentFocus].setTextColor(basicColor);
                tabItem[currentFocus].setTypeface(Typeface.DEFAULT);
                currentFocus = tabBtnId;
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_index);
        initTabs();
    }

    private void initTabs() {
        tabItem[0] = findViewById(R.id.tabIndex);
        tabItem[1] = findViewById(R.id.tabFocus);
        tabItem[2] = findViewById(R.id.tabMsg);
        tabItem[3] = findViewById(R.id.tabMe);
        tabItem[currentFocus].setTextColor(focusedColor);
        tabItem[currentFocus].setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        for(int i = 0; i < 4; i++){
            tabItem[i].setOnClickListener(new BtnOnclickListener(i));
        }
    }
}


