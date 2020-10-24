package com.akash.redcarpetup.news_repository;

import androidx.lifecycle.MutableLiveData;

import com.akash.redcarpetup.model.NewsModel;
import com.akash.redcarpetup.network.ApiClient;
import com.akash.redcarpetup.network.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {

    private static NewsRepository newsRepository;

    public static NewsRepository getInstance(){
        if (newsRepository == null){
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    private ApiInterface apiInterface;

    public NewsRepository(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    public MutableLiveData<NewsModel> getNews(String source, String key){
        final MutableLiveData<NewsModel> newsData = new MutableLiveData<>();
        apiInterface.getLatestNews("us","business", key).enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call,
                                   Response<NewsModel> response) {
                if (response.isSuccessful()){
                    newsData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                newsData.setValue(null);
            }
        });
        return newsData;
    }

}
