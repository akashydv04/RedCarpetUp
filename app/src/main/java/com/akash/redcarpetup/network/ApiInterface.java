package com.akash.redcarpetup.network;

import com.akash.redcarpetup.model.NewsModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("top-headlines")
    Call<NewsModel> getLatestNews(@Query("country") String country,@Query("category") String category, @Query("apiKey") String apiKey);

}
