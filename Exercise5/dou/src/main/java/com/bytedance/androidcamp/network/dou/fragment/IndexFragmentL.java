package com.bytedance.androidcamp.network.dou.fragment;

import android.app.ActivityOptions;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.bytedance.androidcamp.network.dou.IndexActivity;
import com.bytedance.androidcamp.network.dou.LoginActivity;
import com.bytedance.androidcamp.network.dou.R;
import com.bytedance.androidcamp.network.dou.ReviewActivity;
import com.bytedance.androidcamp.network.dou.minterface.args;

public class IndexFragmentL extends Fragment {
    private VideoView videoView;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btn_play;
    private Button refresh;
    private ImageButton btnLike,btn_comment;
    private Animation loadHeartAnimation;

    private int currentPos = 0;

    public static MarqueeTextView marqueeTextView = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_indexl, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        videoView = view.findViewById(R.id.video_container);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        btn_play = view.findViewById(R.id.btn_play);
        btn_comment = view.findViewById(R.id.btn_comment);
        marqueeTextView = view.findViewById(R.id.marquee_text);
        btnLike =view.findViewById(R.id.btn_like);


        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReviewActivity.class);
                //播放动画
                startActivityForResult(intent, 1, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
            }
        });

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(args.isHeart){
                    args.isHeart=false;
                    btnLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_like));
                    loadHeartAnimation = AnimationUtils.loadAnimation(v.getContext(),R.anim.heart_animation);
                    btnLike.startAnimation(loadHeartAnimation);
                }else{
                    args.isHeart=true;
                    btnLike.setImageDrawable(getResources().getDrawable(R.drawable.ic_heart));
                    loadHeartAnimation = AnimationUtils.loadAnimation(v.getContext(),R.anim.heart_animation);
                    btnLike.startAnimation(loadHeartAnimation);
                }
            }
        });

        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!videoView.isPlaying()) {
                    videoView.start();
                    marqueeTextView.startScroll();
                    btn_play.setVisibility(View.INVISIBLE);
                }
            }
        });

        swipeRefreshLayout.setColorSchemeResources(R.color.purple, R.color.mediumvioletred, R.color.mediumpurple);
        currentPos = (int) (1 + Math.random() * (45 - 1 + 1));
        initVideo();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPos = (int) (1 + Math.random() * (45 - 1 + 1));
                initVideo();

            }
        });
        return view;
    }

    private void initVideo() {
        String url = "https://minidouyin.su.bcebos.com/" + currentPos + ".mp4";
        videoView.setMediaController(new MediaController(getActivity()));
        videoView.setVideoURI(Uri.parse(url));
        videoView.requestFocus();
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        //隐藏播放按钮
        btn_play.setVisibility(View.INVISIBLE);
        marqueeTextView.startFor0();

        MediaController mc = new MediaController(getActivity());
        mc.setVisibility(View.INVISIBLE);
        videoView.setMediaController(mc);
//        progressBar.setVisibility(View.VISIBLE);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });
    }


}
