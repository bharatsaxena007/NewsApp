package com.bharat.newsapp.news.repo;

import com.bharat.newsapp.news.model.ArticleResponseModel;
import com.bharat.newsapp.news.model.SourceResponse;
import com.bharat.newsapp.news.network.ApiInterface;

import io.reactivex.Single;

public class NewsRepository {
    ApiInterface apiInterface;

    public NewsRepository(ApiInterface apiInterface) {
        this.apiInterface = apiInterface;
    }

    public Single<SourceResponse> getAllList(String key) {
        return apiInterface.callSourceList(key);
    }

    public Single<ArticleResponseModel> getAllArticle(String key, String source) {
        return apiInterface.callArticle(source, key);
    }
}
