package com.bytedance.androidcamp.network.dou;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bytedance.androidcamp.network.dou.api.IMiniDouyinService;
import com.bytedance.androidcamp.network.dou.fragment.IndexFragmentR;
import com.bytedance.androidcamp.network.dou.minterface.args;
import com.bytedance.androidcamp.network.dou.model.PostVideoResponse;
import com.bytedance.androidcamp.network.dou.util.ResourceUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {
    private static final String TAG = "PostActivity";
    private ImageButton ibPost,ibDraft;
    private ImageView ivCover;
    public Uri mSelectedImage;
    private Uri mSelectedVideo;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_VIDEO = 2;
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(IMiniDouyinService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private IMiniDouyinService miniDouyinService = retrofit.create(IMiniDouyinService.class);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        String pathVideo = getIntent().getStringExtra("url");
        File videoFile = new File(pathVideo);
        if(videoFile!=null) {
            mSelectedVideo = Uri.fromFile(videoFile);
        }
        ivCover = this.findViewById(R.id.iv_cover);
        if(pathVideo!=null){
            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
            mmr.setDataSource(pathVideo);
            Bitmap bitmap = mmr.getFrameAtTime();
            ivCover.setImageBitmap(bitmap);
            mSelectedImage = Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, null,null));
        }
        ivCover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
                ivCover.setImageURI(mSelectedImage);
            }
        });
        ibPost=findViewById(R.id.ib_post);
        ibPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postVideo();
                Intent intent = new Intent(PostActivity.this, IndexActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                Toast.makeText(PostActivity.this,"发布成功~快刷新看看吧",Toast.LENGTH_SHORT).show();
                args.activityList.clear();
                args.hasDraft = false;
                startActivity(intent);
            }
        });
        ibDraft=findViewById(R.id.ib_draft);
        ibDraft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(args.hasDraft == true){
                    Toast.makeText(PostActivity.this,"已保存",Toast.LENGTH_SHORT).show();
                    args.loginState = true;
                    Intent intent = new Intent(PostActivity.this, IndexActivity.class);
                    startActivity(intent);
                    return;
                }
                Intent intent = new Intent(PostActivity.this, IndexActivity.class);
                Toast.makeText(PostActivity.this,"已为您保存了草稿",Toast.LENGTH_SHORT).show();
                for(int i = 0; i < args.activityList.size(); i++){
                    args.activityList.get(i).finish();
                }
                args.activityList.clear();
                args.hasDraft = true;
                startActivity(intent);
            }
        });

    }

    private void postVideo() {
        ibPost.setEnabled(false);
        MultipartBody.Part coverImagePart = getMultipartFromUri("cover_image", mSelectedImage);
        MultipartBody.Part videoPart = getMultipartFromUri("video", mSelectedVideo);
        Call<PostVideoResponse> call = miniDouyinService.postVideo("3170106269","jflin",coverImagePart,videoPart);
        call.enqueue(new Callback<PostVideoResponse>() {
            @Override
            public void onResponse(Call<PostVideoResponse> call, Response<PostVideoResponse> response) {
                if(response.body()!=null&&response.isSuccessful()){
                    ibPost.setEnabled(true);
                    Toast.makeText(PostActivity.this, R.string.success_try_refresh, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostVideoResponse> call, Throwable throwable) {
                ibPost.setEnabled(true);
                Toast.makeText(PostActivity.this, "post fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private MultipartBody.Part getMultipartFromUri(String name, Uri uri) {
        File f = new File(ResourceUtils.getRealPath(PostActivity.this, uri));
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        return MultipartBody.Part.createFormData(name, f.getName(), requestFile);
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
    public void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),
                PICK_IMAGE);
    }

}
