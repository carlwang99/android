package android.wecrowds.com.clock;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.wecrowds.com.clock.Widget.Clock;



public class MainActivity extends AppCompatActivity {

    private View mRootView;
    private Clock mClockView;
    private Runnable tickRunnable = new Runnable() {
        public void run() {
            mClockView.invalidate();
            mHandler.postDelayed(tickRunnable, 1000);
        }
    };
    private Handler mHandler = new Handler();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRootView = findViewById(R.id.root);
        mClockView = findViewById(R.id.clock);

        mHandler.post(tickRunnable);
        mRootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClockView.setShowAnalog(!mClockView.isShowAnalog());
            }
        });

    }
}


