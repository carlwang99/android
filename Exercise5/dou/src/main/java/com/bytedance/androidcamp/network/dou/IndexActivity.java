package com.bytedance.androidcamp.network.dou;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bytedance.androidcamp.network.dou.fragment.FocusFragment;
import com.bytedance.androidcamp.network.dou.fragment.IndexFragmentL;
import com.bytedance.androidcamp.network.dou.fragment.IndexFragmentR;
import com.bytedance.androidcamp.network.dou.fragment.MeFragment;
import com.bytedance.androidcamp.network.dou.fragment.MsgFragment;
import com.bytedance.androidcamp.network.dou.minterface.args;

public class IndexActivity extends AppCompatActivity {
    //tabBar的两种主题（黑/透明）
    final int transparentTheme = Color.parseColor("#00000000");
    final int darkTheme = Color.parseColor("#010102");

    private TextView recommend;
    private TextView located;
    private TextView[] tabItem = new TextView[4];
    private ImageView postVideo;
    private LinearLayout tabBar;
    private LinearLayout tabTop;

    private FocusFragment focusFragment = null;
    private IndexFragmentL indexFragmentL = null;
    private IndexFragmentR indexFragmentR = null;
    private MeFragment meFragment = null;
    private MsgFragment msgFragment = null;
    final int focusedColor = Color.parseColor("#ffffff");

    //区分首页和其它页面未选中文字的颜色
    final int textLightColor = Color.parseColor("#66FFFFFF");
    final int textDarkColor = Color.parseColor("#99999a");
    private int currentFocus = 0;
    private int currentTabTop = 0;

    @Override
    protected void onStop() {
        super.onStop();
        if(indexFragmentL.getActivity()!=null){
            VideoView videoView = indexFragmentL.getActivity().findViewById(R.id.video_container);
            videoView.pause();
            Button button = indexFragmentL.getActivity().findViewById(R.id.btn_play);
            button.setVisibility(View.VISIBLE);
        }
    }

    //自定义按钮点击监听器，可接收tab按钮id
    class BtnOnclickListener implements View.OnClickListener{
        private int tabBtnId;
        public BtnOnclickListener(int id){
            tabBtnId = id;
        }
        @Override
        public void onClick(View v) {
            if(tabBtnId != 0 && args.loginState == false){
                Intent intent = new Intent(IndexActivity.this,LoginActivity.class);
                //播放动画
                startActivityForResult(intent, 1, ActivityOptions.makeSceneTransitionAnimation(IndexActivity.this).toBundle());
                return;
            }
            if(tabBtnId == currentFocus){
                //播放刷新动画
            }
            else{
                currentFocus = tabBtnId;
                //切换主题
                if(currentFocus==0){
                    tabBar.setBackgroundColor(transparentTheme);
                    tabItem[0].setTextColor(focusedColor);
                    for(int i = 1; i < 4; i++){
                        tabItem[i].setTextColor(textLightColor);
                    }
                }
                else{
                    tabBar.setBackgroundColor(darkTheme);
                    for(int i = 0; i < 4; i++){
                        if(currentFocus==i) {
                            tabItem[i].setTextColor(focusedColor);
                            continue;
                        }
                        tabItem[i].setTextColor(textDarkColor);
                    }
                }
                //切换Fragment
                switch (currentFocus){
                    case 0:
                        tabTop.setVisibility(View.VISIBLE);
                        switch(currentTabTop){
                            case 0:
                                if(indexFragmentL==null) indexFragmentL = new IndexFragmentL();
                                replaceFragment(indexFragmentL);
                                currentTabTop = 0;
                                break;
                            case 1:
                                if(indexFragmentR==null) indexFragmentR = new IndexFragmentR();
                                replaceFragment(indexFragmentR);
                                currentTabTop = 1;
                                break;
                        }
                        break;
                    case 1:
                        tabTop.setVisibility(View.INVISIBLE);
                        if(focusFragment==null) focusFragment = new FocusFragment();
                        replaceFragment(focusFragment);
                        break;
                    case 2:
                        tabTop.setVisibility(View.INVISIBLE);
                        if(msgFragment==null) msgFragment = new MsgFragment();
                        replaceFragment(msgFragment);
                        break;
                    case 3:
                        tabTop.setVisibility(View.INVISIBLE);
                        if(meFragment==null) meFragment = new MeFragment();
                        replaceFragment(meFragment);
                        break;
                }
            }
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frag_layout,fragment);
        transaction.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_index);
        initTabs();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(args.hasDraft == true){
            args.loginState = true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //与startActivityForResult中的requestCode对应
        if(requestCode == 1){
            switch (resultCode){
                case 1:
                    args.loginState = true;
                        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                        break;
                        default:break;
            }
        }
    }

    private void initTabs() {
        tabBar = findViewById(R.id.tabBar);
        tabTop = findViewById(R.id.tabTop);
        recommend = findViewById(R.id.recommend);
        located = findViewById(R.id.located);
        tabItem[0] = findViewById(R.id.tabIndex);
        tabItem[1] = findViewById(R.id.tabFocus);
        tabItem[2] = findViewById(R.id.tabMsg);
        tabItem[3] = findViewById(R.id.tabMe);
        postVideo = findViewById(R.id.postVideo);
        //默认聚焦在第一个页面
        tabItem[currentFocus].setTextColor(focusedColor);
        tabTop.bringToFront();
        //切换主题
        if(currentFocus==0){
            tabBar.setBackgroundColor(transparentTheme);
            tabItem[0].setTextColor(focusedColor);
            for(int i = 1; i < 4; i++){
                tabItem[i].setTextColor(textLightColor);
            }
        }
        else{
            tabBar.setBackgroundColor(darkTheme);
            for(int i = 0; i < 4; i++){
                if(currentFocus==i) {
                    tabItem[i].setTextColor(focusedColor);
                    continue;
                }
                tabItem[i].setTextColor(textDarkColor);
            }
        }
        indexFragmentL = new IndexFragmentL();
        replaceFragment(indexFragmentL);

        for(int i = 0; i < 4; i++){
            tabItem[i].setOnClickListener(new BtnOnclickListener(i));
        }

        postVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(args.loginState==false){
                    Intent intent = new Intent(IndexActivity.this,LoginActivity.class);
                    //播放动画
                    startActivityForResult(intent, 1, ActivityOptions.makeSceneTransitionAnimation(IndexActivity.this).toBundle());
                    return;
                }
                else{
                    if(!args.hasDraft){
                        Intent intent = new Intent(IndexActivity.this,CustomCameraActivity.class);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(IndexActivity.this).toBundle());

                    }
                    else{
                        Intent intent = new Intent(IndexActivity.this,PostActivity.class);
                        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(IndexActivity.this).toBundle());
                    }
                    return;
                }
            }
        });

        recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换Fragment
                if(currentTabTop == 1){
                    if(indexFragmentL==null) indexFragmentL = new IndexFragmentL();
                    replaceFragment(indexFragmentL);
                    located.setTextColor(textLightColor);
                    located.setTypeface(Typeface.DEFAULT);
                    recommend.setTextColor(focusedColor);
                    recommend.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
                    currentTabTop = 0;
                }
            }
        });

        located.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentTabTop == 0){
                    if(indexFragmentR==null) indexFragmentR = new IndexFragmentR();
                    replaceFragment(indexFragmentR);
                    recommend.setTextColor(textLightColor);
                    recommend.setTypeface(Typeface.DEFAULT);
                    located.setTextColor(focusedColor);
                    located.setTypeface(Typeface.DEFAULT,Typeface.BOLD);
                    currentTabTop = 1;
                }
            }
        });
    }
}


