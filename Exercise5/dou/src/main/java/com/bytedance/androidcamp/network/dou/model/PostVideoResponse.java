package com.bytedance.androidcamp.network.dou.model;

import com.google.gson.annotations.SerializedName;

public class PostVideoResponse {
    @SerializedName("success") private boolean success;

    public boolean isSuccess() {
        return success;
    }
}
