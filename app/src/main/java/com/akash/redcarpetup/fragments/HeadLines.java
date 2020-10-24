package com.akash.redcarpetup.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akash.redcarpetup.R;
import com.akash.redcarpetup.adapter.HeadlinesAdapter;
import com.akash.redcarpetup.model.Article;
import com.akash.redcarpetup.model.NewsModel;
import com.akash.redcarpetup.network.ApiClient;
import com.akash.redcarpetup.network.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HeadLines extends Fragment {

    public static final String API_KEY = "47dadc6821f043d5983c9e34e1d0f6f7";
    private static final String TAG = "headlines";

    private NavController navController;
    private RecyclerView headlinesRecycler;
    private HeadlinesAdapter headlinesAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_head_lines, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        headlinesRecycler = view.findViewById(R.id.headlines_recycler);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL, false);

        headlinesRecycler.setLayoutManager(linearLayoutManager);

        final ApiInterface apiInterface =
                ApiClient.getClient().create(ApiInterface.class);

        Call<NewsModel> call = apiInterface.getLatestNews("us","business", API_KEY);

        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {

                if (response.body().getStatus().equals("ok")){
                    final List<Article> articles;
                    articles = response.body().getArticles();
                    if (articles.size()>0){
                        headlinesAdapter = new HeadlinesAdapter(getActivity(), R.layout.article_card, articles);
                        headlinesRecycler.setAdapter(headlinesAdapter);

                        headlinesAdapter.setOnItemClickListener(new HeadlinesAdapter.OnItemClickListener() {
                            @Override
                            public void onNewsClick(int position) {
                                Article article = articles.get(position);
                                HeadLinesDirections.ActionHeadLinesToDetailedNews action =
                                        HeadLinesDirections.actionHeadLinesToDetailedNews();
                                action.setNewsUrl(article.getUrl());
                                navController.navigate(action);
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Log.e(TAG, "onFailure: "+t.getMessage() );
            }
        });
    }
}