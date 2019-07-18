package com.bytedance.androidcamp.network.dou;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class ReviewActivity extends AppCompatActivity {
    private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //用于告诉Window页面切换需要使用动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_review);
        linearLayout = findViewById(R.id.linear_layout);

        //用于设置窗口高度
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay();

        Window win = this.getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int)(d.getHeight()*0.85);
        lp.gravity = Gravity.BOTTOM;
        win.setAttributes(lp);


    }
}
