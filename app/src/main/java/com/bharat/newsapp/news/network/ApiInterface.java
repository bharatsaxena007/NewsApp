package com.bharat.newsapp.news.network;

import com.bharat.newsapp.news.model.ArticleResponseModel;
import com.bharat.newsapp.news.model.SourceResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("v2/top-headlines/sources")
    Single<SourceResponse> callSourceList(@Query("apiKey") String apiKey);

    @GET("v2/top-headlines")
    Single<ArticleResponseModel> callArticle(@Query("sources") String sources, @Query("apiKey") String apikey);
}
