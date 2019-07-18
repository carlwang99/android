package com.bytedance.camera.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.VideoView;

import com.bytedance.camera.demo.utils.Utils;

public class RecordVideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private static final int REQUEST_VIDEO_CAPTURE = 1;

    private static final int REQUEST_EXTERNAL_CAMERA = 101;
    String[] permissions = new String[] {

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);
        videoView = findViewById(R.id.img);
        findViewById(R.id.btn_picture).setOnClickListener(v -> {

            if (Utils.isPermissionsReady(this, permissions)) {
                //todo 打开摄像机
                openVideoRecordApp();
            } else {
                //todo 权限检查
                Utils.reuqestPermissions(this, permissions, REQUEST_EXTERNAL_CAMERA);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            //todo 播放刚才录制的视频

        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_CAMERA: {
                //todo 判断权限是否已经授予
                if (Utils.isPermissionsReady(this, permissions)) {
                    //todo 打开摄像机
                    openVideoRecordApp();
                }
                break;
            }
        }
    }

    private void openVideoRecordApp() {

    }
}
