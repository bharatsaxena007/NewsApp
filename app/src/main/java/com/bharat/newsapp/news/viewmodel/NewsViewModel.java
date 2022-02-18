package com.bharat.newsapp.news.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bharat.newsapp.news.model.ArticleResponseModel;
import com.bharat.newsapp.news.model.SourceResponse;
import com.bharat.newsapp.news.repo.NewsRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;


public class NewsViewModel extends ViewModel {

    public MutableLiveData<SourceResponse> newsListResponse = new MutableLiveData<>();
    public MutableLiveData<ArticleResponseModel> articleResponse = new MutableLiveData<>();
    public MutableLiveData<Boolean> mIsLoading = new MutableLiveData<>();
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    NewsRepository newsRepository;

    NewsViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void callNewsSourceApi(String key) {
        mIsLoading.setValue(true);
        compositeDisposable.add(newsRepository.getAllList(key).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((sourceResponse, throwable) -> {
                    newsListResponse.postValue(sourceResponse);
                    mIsLoading.setValue(false);
                }));
    }

    public void callArticleApi(String key, String source) {
        mIsLoading.setValue(true);
        compositeDisposable.add(newsRepository.getAllArticle(key, source).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((articleResponseModel, throwable) -> {
                    articleResponse.postValue(articleResponseModel);
                    mIsLoading.postValue(false);
                }));
    }

    @Override
    protected void onCleared() {
        compositeDisposable.clear();
    }
}
