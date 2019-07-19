package com.bytedance.androidcamp.network.demo.newtork;

import com.bytedance.androidcamp.network.demo.model.Cat;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ICatService {
    String HOST = "https://api.thecatapi.com/";
    String PATH = "v1/images/search";

    @GET(PATH)
    Call<List<Cat>> randomCat(@Query("limit") int limit);
}
