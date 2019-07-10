package android.wecrowds.com.lottie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    private SeekBar msb;
    private LottieAnimationView mlv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        msb = findViewById(R.id.msb);
        mlv = findViewById(R.id.mlv);
        msb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            //mlv的progress设置的时候注意progress在0-1之间
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mlv.pauseAnimation();
                mlv.setProgress(progress/100f);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
