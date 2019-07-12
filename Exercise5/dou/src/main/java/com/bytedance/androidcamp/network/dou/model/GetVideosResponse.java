package com.bytedance.androidcamp.network.dou.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetVideosResponse {
    @SerializedName("success") private boolean success;
    @SerializedName("feeds") private List<Video> videos;

    public List<Video> getVideos() {
        return videos;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setVideos(List<Video> videos) {
        this.videos = videos;
    }

}
