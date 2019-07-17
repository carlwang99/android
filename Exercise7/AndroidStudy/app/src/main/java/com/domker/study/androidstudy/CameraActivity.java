package com.domker.study.androidstudy;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.Locale;

public class CameraActivity extends AppCompatActivity {
    private final static int REQUEST_CAMERA = 123;
    private final static int REQUEST_IMAGE = 124;
    private ImageView mImageView;
    private File imagePath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        mImageView = findViewById(R.id.imageView);

        findViewById(R.id.buttonCamera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSystemCamera();
            }
        });

        findViewById(R.id.buttonPick).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSystemGallery();
            }
        });
    }

    /**
     * 打开系统相册
     */
    private void openSystemGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_IMAGE);
    }

    /**
     * 打开系统相机
     */
    private void openSystemCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String imageName = String.format(Locale.getDefault(), "img_%d.jpg", System.currentTimeMillis());
        imagePath = new File(Environment.getExternalStorageDirectory(), imageName);
        Uri outUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", imagePath);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        startActivityForResult(cameraIntent, REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            Glide.with(this).load(imagePath).into(mImageView);
        } else if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK) {
            if (data != null && data.getData() != null) {
                Glide.with(this).load(data.getData()).into(mImageView);
            }
        }
    }
}
