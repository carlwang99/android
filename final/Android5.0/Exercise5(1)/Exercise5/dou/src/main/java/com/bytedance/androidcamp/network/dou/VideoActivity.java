package com.bytedance.androidcamp.network.dou;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;
import android.widget.Button;
import com.bytedance.androidcamp.network.dou.minterface.args;

import com.bytedance.androidcamp.network.dou.fragment.MarqueeTextView;
import com.bytedance.androidcamp.network.dou.model.Video;

public class VideoActivity extends AppCompatActivity {
    static public MarqueeTextView marqueeTextView;
    private ProgressBar progressBar;
    private VideoView videoView;
    private Button btn_play;
    private ImageButton btn_comment;
    private ImageButton btnLike;
    private Animation loadHeartAnimation;
    private ImageView iv_heart;


    public static void launch(Activity activity, String url) {
        Intent intent = new Intent(activity, VideoActivity.class);
        intent.putExtra("url", url);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        videoView.pause();
        btn_play.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
        args.isInner = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        args.isInner = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        marqueeTextView = findViewById(R.id.inner_marquee);
        marqueeTextView.startFor0();
        btn_play = findViewById(R.id.btn_play);
        btnLike = findViewById(R.id.btn_like);

        btn_comment = findViewById(R.id.btn_comment);
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(VideoActivity.this, ReviewActivity.class);
                //播放动画
                startActivityForResult(intent, 1, ActivityOptions.makeSceneTransitionAnimation(VideoActivity.this).toBundle());

            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(args.isInnerHeart){
                    args.isInnerHeart=false;
                    btnLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
                    loadHeartAnimation = AnimationUtils.loadAnimation(v.getContext(),R.anim.heart_animation);
                    btnLike.startAnimation(loadHeartAnimation);
                }else{
                    args.isInnerHeart=true;
                    btnLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart));
                    loadHeartAnimation = AnimationUtils.loadAnimation(v.getContext(),R.anim.heart_animation);
                    btnLike.startAnimation(loadHeartAnimation);
                }
            }
        });

        String url = getIntent().getStringExtra("url");
        videoView = findViewById(R.id.video_container);
        progressBar = findViewById(R.id.progress_bar);
        videoView.setMediaController(new MediaController(this));
        videoView.setVideoURI(Uri.parse(url));
        videoView.requestFocus();
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
            }
        });
        final Button btn_play=findViewById(R.id.btn_play);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!videoView.isPlaying()){
                    videoView.start();
                    marqueeTextView.stopScroll();
                    btn_play.setVisibility(View.INVISIBLE);
                }
            }
        });
        MediaController mc = new MediaController(this);
        mc.setVisibility(View.INVISIBLE);
        videoView.setMediaController(mc);
        progressBar.setVisibility(View.VISIBLE);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });
    }
}
