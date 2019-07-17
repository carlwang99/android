package com.domker.study.androidstudy;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;

import java.io.IOException;

public class Player {
    private MediaPlayer mp;//MediaPlayer.create(context, R.raw.hourse);//这时就不用调用setDataSource了

    public Player(Context context) {
        mp = MediaPlayer.create(context, R.raw.yuminhong);//这时就不用调用setDataSource了
    }

    public void play() {
        // MediaPlayer的setDataSource一共四个方法：
        if (mp.isPlaying()) {
            mp.pause();
        } else {
            mp.start();
        }

//        mp.setDataSource (String path);
//        mp.setDataSource (FileDescriptor fd);
//        mp.setDataSource (Context context, Uri uri);
//        mp.setDataSource (FileDescriptor fd, long offset, long length);

//        IjkMediaPlayer ijkMediaPlayer = new IjkMediaPlayer();
//        ijkMediaPlayer.native_setLogLevel(IjkMediaPlayer.IJK_LOG_DEBUG);
//        //开启硬解码
//        ijkMediaPlayer.setOption(IjkMediaPlayer.OPT_CATEGORY_PLAYER, "mediacodec", 1);
//
//        ijkMediaPlayer.setDataSource(mPath);
//        //给mediaPlayer设置视图
//        ijkMediaPlayer.setDisplay(surfaceView.getHolder());
//        ijkMediaPlayer.prepareAsync();
//        ijkMediaPlayer.start();

//        MediaRecorder mr = new MediaRecorder();
//        mr.setAudioSource(MediaRecorder.AudioSource.MIC);  //音频输入源
//        mr.setOutputFormat(MediaRecorder.OutputFormat.AMR_WB);   //设置输出格式
//        mr.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_WB);   //设置编码格式
//        mr.setOutputFile(soundFile.getAbsolutePath());
//        try {
//            mr.prepare();
//            mr.start();  //开始录制
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//        MediaRecorder mediarecorder = new MediaRecorder();// 创建mediarecorder对象
//        // 设置录制视频源为Camera(相机)
//        mediarecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
//        // 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
//        mediarecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
//        // 设置录制的视频编码h263 h264
//        mediarecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
//        // 设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
//        mediarecorder.setVideoSize(300, 200);
//        // 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
//        mediarecorder.setVideoFrameRate(20);
//        mediarecorder.setPreviewDisplay(surfaceHolder.getSurface());
//        // 设置视频文件输出的路径
//        mediarecorder.setOutputFile("/sdcard/love.3gp");
//        try {
//            // 准备录制
//            mediarecorder.prepare();
//            // 开始录制
//            mediarecorder.start();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

//        videoView = findViewById(R.id.videoView);
//        videoView.setVideoPath(getVideoPath(R.raw.yuminhong));
//        buttonPause = findViewById(R.id.buttonPause);
//        buttonPause.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoView.pause();
//            }
//        });
//        buttonPlay = findViewById(R.id.buttonPlay);
//        buttonPlay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoView.start();
//            }
//        });
    }



}
