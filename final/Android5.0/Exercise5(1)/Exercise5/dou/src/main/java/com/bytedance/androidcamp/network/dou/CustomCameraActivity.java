package com.bytedance.androidcamp.network.dou;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bytedance.androidcamp.network.dou.minterface.args;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import static com.bytedance.androidcamp.network.dou.utils.Utils.MEDIA_TYPE_IMAGE;
import static com.bytedance.androidcamp.network.dou.utils.Utils.MEDIA_TYPE_VIDEO;
import static com.bytedance.androidcamp.network.dou.utils.Utils.getOutputMediaFile;

public class CustomCameraActivity extends AppCompatActivity {
    private SurfaceView mSurfaceView;
    private Camera mCamera;
    private String videoOutputFile;
    private String imageOutputFile;
    private File pictureFile;
    private ImageView imageViewCircle;
    private int CAMERA_TYPE = Camera.CameraInfo.CAMERA_FACING_BACK;
    private int CAMERA_FRONT = Camera.CameraInfo.CAMERA_FACING_FRONT;
    private boolean isRecording = false;
    private ImageView ivLight,ivRecord;
    private TextView tvLight;
    private int rotationDegree = 0;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_VIDEO = 2;
    private static final String TAG = "CustomCameraActivity";
    public Uri mSelectedImage;
    private Uri mSelectedVideo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args.activityList.add(CustomCameraActivity.this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_custom_camera);

        mCamera = getCamera(CAMERA_TYPE);
        mSurfaceView = findViewById(R.id.img);
        imageViewCircle = findViewById(R.id.iv_outCircle);
        final ObjectAnimator objectAnimator =ObjectAnimator.ofFloat(imageViewCircle,"scaleX",1.0f,0.8f);
        final ObjectAnimator objectAnimator1 =ObjectAnimator.ofFloat(imageViewCircle,"scaleY",1.0f,0.8f);
        final ObjectAnimator objectAnimatorAlpha =ObjectAnimator.ofFloat(imageViewCircle,"alpha",1.0f,0.6f);
        //todo 给SurfaceHolder添加Callback
        SurfaceHolder surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if(mCamera!=null){
                        mCamera.setPreviewDisplay(holder);
                        mCamera.startPreview();
                    }else{
                        if(CAMERA_TYPE==0){
                            mCamera=getCamera(CAMERA_TYPE);
                        }else{
                            mCamera=getCamera(CAMERA_FRONT);
                        }
                        mCamera.setPreviewDisplay(holder);
                        mCamera.startPreview();

                    }
                    AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) ((ImageView) findViewById(R.id.iv_record)).getDrawable();
                    drawable.reset();

                    zoomAuto();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
        });

        findViewById(R.id.iv_album).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomCameraActivity.this.chooseVideo();
                Intent intent = new Intent(CustomCameraActivity.this, PostActivity.class);
                if (mSelectedVideo != null) {
                    String path = getPathFromUri(CustomCameraActivity.this, mSelectedVideo);
                    intent.putExtra("url", path);
                    CustomCameraActivity.this.startActivity(intent);
                }
            }
        });

        findViewById(R.id.iv_record).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 录制，第一次点击是start，第二次点击是stop
                AnimatedVectorDrawable drawable = (AnimatedVectorDrawable) ((ImageView) CustomCameraActivity.this.findViewById(R.id.iv_record)).getDrawable();

                if (isRecording) {
                    //todo 停止录制
                    CustomCameraActivity.this.videoStartPreview();
                    CustomCameraActivity.this.releaseMediaRecorder();
                    isRecording = false;
                    objectAnimator.pause();
                    objectAnimator1.pause();
                    objectAnimatorAlpha.pause();
                } else {
                    //todo 录制
                    if (CustomCameraActivity.this.prepareVideoRecorder())
                        isRecording = true;
                    if (drawable.isRunning()) {
                        drawable.stop();
                    } else {
                        drawable.start();
                    }
                    objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
                    objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
                    objectAnimator.setDuration(500);
                    objectAnimator.start();
                    objectAnimator1.setRepeatMode(ValueAnimator.REVERSE);
                    objectAnimator1.setRepeatCount(ValueAnimator.INFINITE);
                    objectAnimator1.setDuration(500);
                    objectAnimator1.start();
                    objectAnimatorAlpha.setRepeatMode(ValueAnimator.REVERSE);
                    objectAnimatorAlpha.setRepeatCount(ValueAnimator.INFINITE);
                    objectAnimatorAlpha.setDuration(500);
                    objectAnimatorAlpha.start();
                }
            }
        });

        //判断闪光灯状态并开启
        ivLight=findViewById(R.id.iv_light);
        tvLight=findViewById(R.id.tv_light);
        ivLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CAMERA_TYPE== Camera.CameraInfo.CAMERA_FACING_BACK){
                    Camera.Parameters parameters = mCamera.getParameters();
                    String flashMode =parameters.getFlashMode();
                    if(flashMode.equals(Camera.Parameters.FLASH_MODE_OFF)){
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                        ivLight.setImageDrawable(getResources().getDrawable(R.drawable.ic_light_on));
                    }else {
                        ivLight.setImageDrawable(getResources().getDrawable(R.drawable.ic_light));
                        parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                    }
                    mCamera.setParameters(parameters);

                }
            }
        });
        findViewById(R.id.iv_facing).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo 切换前后摄像头
                CustomCameraActivity.this.releaseCameraAndPreview();
                if (CAMERA_TYPE == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    CAMERA_TYPE = Camera.CameraInfo.CAMERA_FACING_FRONT;
                    ivLight.setVisibility(View.INVISIBLE);
                    tvLight.setVisibility(View.INVISIBLE);

                } else {
                    CAMERA_TYPE = Camera.CameraInfo.CAMERA_FACING_BACK;
                    ivLight.setVisibility(View.VISIBLE);
                    tvLight.setVisibility(View.VISIBLE);
                }
                try {
                    mCamera = CustomCameraActivity.this.getCamera(CAMERA_TYPE);
                    mCamera.setPreviewDisplay(mSurfaceView.getHolder());
                    CustomCameraActivity.this.zoomAuto();
                    mCamera.startPreview();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    public void chooseVideo() {
            Intent intent = new Intent();
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult() called with: requestCode = ["
                + requestCode
                + "], resultCode = ["
                + resultCode
                + "], data = ["
                + data
                + "]");

        if (resultCode == RESULT_OK && null != data) {
            if (requestCode == PICK_IMAGE) {
                mSelectedImage = data.getData();
                Log.d(TAG, "selectedImage = " + mSelectedImage);
            } else if (requestCode == PICK_VIDEO) {
                mSelectedVideo = data.getData();
                Log.d(TAG, "mSelectedVideo = " + mSelectedVideo);
            }
        }
    }

    public Camera getCamera(int position) {
        CAMERA_TYPE = position;
        if (mCamera != null) {
            releaseCameraAndPreview();
        }
        Camera cam = Camera.open(position);
        rotationDegree = getCameraDisplayOrientation(position);
        cam.setDisplayOrientation(rotationDegree);

        return cam;
    }





    private  void zoomAuto(){
        if(CAMERA_TYPE==Camera.CameraInfo.CAMERA_FACING_BACK){
            Camera.Parameters parameters = mCamera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            List<String> modes = parameters.getSupportedFocusModes();
            for(String mode : modes) {
                if(mode.equals(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)){
                    parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
                    break;
                }
            }
            mCamera.setParameters(parameters);
        }
    }


    private static final int DEGREE_90 = 90;
    private static final int DEGREE_180 = 180;
    private static final int DEGREE_270 = 270;
    private static final int DEGREE_360 = 360;

    private int getCameraDisplayOrientation(int cameraId) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = DEGREE_90;
                break;
            case Surface.ROTATION_180:
                degrees = DEGREE_180;
                break;
            case Surface.ROTATION_270:
                degrees = DEGREE_270;
                break;
            default:
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % DEGREE_360;
            result = (DEGREE_360 - result) % DEGREE_360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + DEGREE_360) % DEGREE_360;
        }
        return result;
    }


    private void releaseCameraAndPreview() {
        //todo 释放camera资源
        if(mCamera!=null){
            mCamera.setPreviewCallback(null);
            mCamera.stopPreview();
            mCamera.release();
            mCamera=null;
        }

    }

    Camera.Size size;

    private void videoStartPreview() {
        //todo 开始预览视频
        Intent spIntent = new Intent(CustomCameraActivity.this,VideoPreviewActivity.class);
        spIntent.putExtra("url",videoOutputFile);
        startActivity(spIntent);
    }


    private MediaRecorder mMediaRecorder;

    private boolean prepareVideoRecorder() {
        //todo 准备MediaRecorder
        mMediaRecorder = new MediaRecorder();
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));
        mMediaRecorder.setOutputFile(getOutputMediaFile(MEDIA_TYPE_VIDEO).toString());
        mMediaRecorder.setPreviewDisplay(mSurfaceView.getHolder().getSurface());
        mMediaRecorder.setOrientationHint(rotationDegree);
        videoOutputFile = getOutputMediaFile(MEDIA_TYPE_VIDEO).toString();
        try{
            mMediaRecorder.prepare();
            mMediaRecorder.start();
        }catch (Exception e){
            releaseMediaRecorder();
            return false;
        }
        return true;
    }


    private void releaseMediaRecorder() {
        //todo 释放MediaRecorder
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder=null;
        mCamera.lock();

    }


    private Camera.PictureCallback mPicture = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
            if (pictureFile == null) {
                return;
            }
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                fos.write(data);
                fos.close();
                MediaScannerConnection.scanFile(CustomCameraActivity.this, new String[]{imageOutputFile}, null, null);
            } catch (IOException e) {
                Log.d("mPicture", "Error accessing file: " + e.getMessage());
            }

            mCamera.startPreview();
        }
    };

    @SuppressLint("NewApi")
    public static String getPathFromUri(final Context context, final Uri uri) {
        if (uri == null) {
            return null;
        }
        // 判斷是否為Android 4.4之後的版本
        final boolean after44 = Build.VERSION.SDK_INT >= 19;
        if (after44 && DocumentsContract.isDocumentUri(context, uri)) {
            // 如果是Android 4.4之後的版本，而且屬於文件URI
            final String authority = uri.getAuthority();
            // 判斷Authority是否為本地端檔案所使用的
            if ("com.android.externalstorage.documents".equals(authority)) {
                // 外部儲存空間
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] divide = docId.split(":");
                final String type = divide[0];
                if ("primary".equals(type)) {
                    String path = Environment.getExternalStorageDirectory().getAbsolutePath().concat("/").concat(divide[1]);
                    return path;
                } else {
                    String path = "/storage/".concat(type).concat("/").concat(divide[1]);
                    return path;
                }
            } else if ("com.android.providers.downloads.documents".equals(authority)) {
                // 下載目錄
                final String docId = DocumentsContract.getDocumentId(uri);
                if (docId.startsWith("raw:")) {
                    final String path = docId.replaceFirst("raw:", "");
                    return path;
                }
                final Uri downloadUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.parseLong(docId));
                String path = queryAbsolutePath(context, downloadUri);
                return path;
            } else if ("com.android.providers.media.documents".equals(authority)) {
                // 圖片、影音檔案
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] divide = docId.split(":");
                final String type = divide[0];
                Uri mediaUri = null;
                if ("image".equals(type)) {
                    mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    mediaUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    mediaUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                } else {
                    return null;
                }
                mediaUri = ContentUris.withAppendedId(mediaUri, Long.parseLong(divide[1]));
                String path = queryAbsolutePath(context, mediaUri);
                return path;
            }
        } else {
            // 如果是一般的URI
            final String scheme = uri.getScheme();
            String path = null;
            if ("content".equals(scheme)) {
                // 內容URI
                path = queryAbsolutePath(context, uri);
            } else if ("file".equals(scheme)) {
                // 檔案URI
                path = uri.getPath();
            }
            return path;
        }
        return null;
    }

    public static String queryAbsolutePath(final Context context, final Uri uri) {
        final String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                return cursor.getString(index);
            }
        } catch (final Exception ex) {
            ex.printStackTrace();
            if (cursor != null) {
                cursor.close();
            }
        }
        return null;
    }
}
