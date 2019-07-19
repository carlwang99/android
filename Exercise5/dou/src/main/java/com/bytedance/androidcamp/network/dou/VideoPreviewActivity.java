package com.bytedance.androidcamp.network.dou;

import android.content.Intent;
import android.hardware.Camera;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bytedance.androidcamp.network.dou.minterface.args;

import java.io.File;

public class VideoPreviewActivity extends AppCompatActivity {
    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                System.out.println("删除单个文件" + fileName + "成功！");
                return true;
            } else {
                System.out.println("删除单个文件" + fileName + "失败！");
                return false;
            }
        } else {
            System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_preview);
        final String path = getIntent().getStringExtra("url");

        args.activityList.add(this);

        VideoView videoView = findViewById(R.id.video_view);
        videoView.setVideoPath(path);
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.setLooping(true);
                mp.start();
            }
        });
        videoView.start();
        ImageView btnOk,btnCancel;
        btnOk = findViewById(R.id.btn_ok);
        btnCancel = findViewById(R.id.btn_cancel);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(VideoPreviewActivity.this,PostActivity.class);
                intent.putExtra("url",path);
                startActivity(intent);
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(deleteFile(path)){
                    Toast.makeText(VideoPreviewActivity.this,"成功删除视频",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(VideoPreviewActivity.this,"删除视频失败",Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }


}
