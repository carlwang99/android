package helloworld.wecrowds.com.exercise1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

//使用到六种控件 TextView，EditView，Button，ImageView，ProgressBar，CheckBox

public class Exercise1Activity extends AppCompatActivity {
    private TextView wh1;
    private Button login;
    private ProgressBar loginPro;
    private CheckBox c1;
    private CheckBox c2;
    private TextView forget;
    private boolean flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise1);
        forget = findViewById(R.id.forget);
        forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Exercise1Activity.this,"啊，找回系统还没开放",Toast.LENGTH_SHORT).show();
                Log.d("忘记密码","用户点击了忘记密码！");
            }
        });
        c1 = findViewById(R.id.c1);
        c2 = findViewById(R.id.c2);

        c1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(Exercise1Activity.this,"记住密码",Toast.LENGTH_SHORT).show();
                    Log.d("记住密码","用户选择了记住密码！");
                }
//                else{
//                    Toast.makeText(Exercise1Activity.this,"已取消",Toast.LENGTH_SHORT).show();
//                    Log.d("取消记住密码","用户取消了记住密码！");
//                }
            }
        });
        c2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(Exercise1Activity.this,"自动登录",Toast.LENGTH_SHORT).show();
                    Log.d("自动能录","用户选择了自动登录！");
                }
            }
        });

        login = findViewById(R.id.login);
        loginPro = findViewById(R.id.loginPro);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("点击了登录按钮","登录接口尚未实现");
                Toast.makeText(Exercise1Activity.this,"网络异常！",Toast.LENGTH_SHORT).show();
            }
        });

        wh1 = findViewById(R.id.hw);
        wh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("点击了浙江大学文字","浙江大学欢迎您！");
                Toast.makeText(Exercise1Activity.this,"浙江大学欢迎您！",Toast.LENGTH_SHORT).show();
                if(!flag) {
                    wh1.setText("Zhejiang University");
                    flag = !flag;
                }
                else{
                    wh1.setText("浙江大学");
                    flag = !flag;
                }
                int progress = loginPro.getProgress();
                progress = progress + 10;
                loginPro.setProgress(progress);
            }
        });

    }
}
