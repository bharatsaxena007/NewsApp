package com.bharat.newsapp.news.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bharat.newsapp.news.repo.NewsRepository;

public class NewsViewModelFactory implements ViewModelProvider.Factory {
    NewsRepository newsRepository;
    public NewsViewModelFactory(NewsRepository repo)
    {
      this.newsRepository = repo;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(NewsViewModel.class))
            return (T) new NewsViewModel(this.newsRepository);
        else
            throw new IllegalArgumentException("ViewModel not found");

    }
}
