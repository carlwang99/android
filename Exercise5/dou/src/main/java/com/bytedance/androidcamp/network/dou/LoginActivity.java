package com.bytedance.androidcamp.network.dou;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private TextView quitText;
    private EditText phoneNumText, vCode;
    private TextView fetchVCode;
    private ImageView loginBtn;
    private Handler mHandler = new Handler();
    private int ticks;
    private Runnable tickRunnable = new Runnable() {
        public void run() {
            ticks = ticks - 1;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if(ticks == 0){
                        fetchVCode.setClickable(true);
                        fetchVCode.setText("重新发送");
                    }
                    else{
                        fetchVCode.setText(ticks+"s");
                        mHandler.postDelayed(tickRunnable, 1000);
                    }
                }
            });
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //用于告诉Window页面切换需要使用动画
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        Transition slide = TransitionInflater.from(this).inflateTransition(R.transition.slide);
        setContentView(R.layout.activity_login);
        initView();
        Window win = this.getWindow();
        win.setEnterTransition(slide);
        win.setExitTransition(slide);
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        win.setAttributes(lp);

    }

    private void initView() {
        quitText = findViewById(R.id.quit);
        quitText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        phoneNumText = findViewById(R.id.phoneNum);
        vCode = findViewById(R.id.vCode);
        fetchVCode = findViewById(R.id.fetchVCode);
        loginBtn = findViewById(R.id.loginBtn);

        //3-4-4分割/点亮发送验证码按钮
        phoneNumText.addTextChangedListener(new TextWatcher() {

            private CharSequence tmp;
            private int oldLength = 0;
            private int sLastLength = 0;
            private boolean isChange = true;
            private boolean delete = false;
            private int curLength = 0;
            private int emptyNumB = 0;  //初始空格数
            private int emptyNumA = 0;  //遍历添加空格后的空格数

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tmp = s;
                oldLength = s.length();
                emptyNumB = 0;
                for (int i = 0; i < s.toString().length(); i++) {
                    if (s.charAt(i) == ' ') emptyNumB++;
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                curLength = s.length();
                //优化处理,如果长度未改变或则改变后长度小于3就不需要添加空格
                if (curLength == oldLength || curLength <= 3) {
                    isChange = false;
                } else {
                    isChange = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChange) {
                    int basicColor = Color.parseColor("#dcc3ff");
                    int focusedColor = Color.parseColor("#ffffff");
                    if(s.length()==13) {
                        fetchVCode.setTextColor(focusedColor);
                        fetchVCode.setClickable(true);
                    }
                    else{
                        fetchVCode.setTextColor(basicColor);
                        fetchVCode.setClickable(false);
                    }
                    if (curLength - sLastLength < 0) {  //判断是editext中的字符串是在减少 还是在增加
                        delete = true;
                    } else {
                        delete = false;
                    }
                    sLastLength = curLength;
                    int selectIndex = phoneNumText.getSelectionEnd();//获取光标位置
                    String content = s.toString().replaceAll(" ", "");
                    StringBuffer sb = new StringBuffer(content);
                    //遍历加空格
                    int index = 1;
                    emptyNumA = 0;
                    for (int i = 0; i < content.length(); i++) {
                        if (i == 2) {

                            sb.insert(i + index, " ");
                            index++;
                            emptyNumA++;
                        } else if (i == 6) {
                            sb.insert(i + index, " ");
                            index++;
                            emptyNumA++;
                        }
                    }
                    String result = sb.toString();
                    //遍历加空格后 如果发现最后一位是空格 就把这个空格去掉
                    if (result.endsWith(" ")) {
                        result = result.substring(0, result.length() - 1);
                        emptyNumA--;
                    }

                    s.replace(0, s.length(), result);

                    //处理光标位置
                    if (emptyNumA > emptyNumB){
                        selectIndex = selectIndex + (emptyNumA - emptyNumB);
                    }
                    if (selectIndex > result.length()) {
                        selectIndex = result.length();
                    } else if (selectIndex < 0) {
                        selectIndex = 0;
                    }
                    // 例如"123 45"且光标在4后面 这时需要删除4 光标的处理
                    if (selectIndex > 1 && s.charAt(selectIndex - 1) == ' ') {
                        if (delete) {
                            selectIndex--;
                        } else {
                            selectIndex++;
                        }
                    }

                    phoneNumText.setSelection(selectIndex);
                    isChange = false;
                }
            }
        });

        //获取验证码按钮
        fetchVCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(LoginActivity.this, "验证码已发送", Toast.LENGTH_SHORT).show();
                fetchVCode.setClickable(false);
                ticks = 60;
                mHandler.post(tickRunnable);
            }
        });

        //登录按钮
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(vCode.getText().toString().length() != 6){
                    Toast.makeText(LoginActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent();
                    intent.putExtra("resultCode", "登录成功");
                    setResult(1, intent);
                    finish();
                }
            }
        });
    }
}
