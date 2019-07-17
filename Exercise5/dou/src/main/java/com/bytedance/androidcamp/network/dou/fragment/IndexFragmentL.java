package com.bytedance.androidcamp.network.dou.fragment;

import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bytedance.androidcamp.network.dou.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class IndexFragmentL extends Fragment {
    private VideoView videoView;
    private ProgressBar progressBar;
//    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btn_play;
    private Button refresh;
    private int currentPos = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_indexl, container, false);
        progressBar = view.findViewById(R.id.progress_bar);
        videoView = view.findViewById(R.id.video_container);
//        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        btn_play = view.findViewById(R.id.btn_play);
        refresh = view.findViewById(R.id.refresh);

        Random rm = new Random();
        currentPos = rm.nextInt(12);

        initVideo();

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(currentPos == 12) currentPos = 0;
                else currentPos = currentPos + 1;
                initVideo();
            }
        });
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//                if(currentPos == 12) currentPos = 0;
//                else currentPos = currentPos + 1;
//                initVideo();
//            }
//        });
        return view;
    }

    private void initVideo() {
        String url = "https://minidouyin.su.bcebos.com/"+currentPos+".mp4";
        videoView.setMediaController(new MediaController(getActivity()));
        videoView.setVideoURI(Uri.parse(url));
        videoView.requestFocus();
        videoView.start();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                progressBar.setVisibility(View.GONE);
            }
        });
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!videoView.isPlaying()){
                    videoView.start();
                    btn_play.setVisibility(View.INVISIBLE);
                }
            }
        });
        MediaController mc = new MediaController(getActivity());
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
