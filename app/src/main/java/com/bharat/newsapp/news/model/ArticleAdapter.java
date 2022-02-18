package com.bharat.newsapp.news.model;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharat.newsapp.R;
import com.bharat.newsapp.databinding.ArticleItemBinding;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    public ArrayList<ArticleResponseModel.Article> newsSourceList;
    SourceOnClickListener sourceOnClickListener;

    public ArticleAdapter(ArrayList<ArticleResponseModel.Article> actionList, SourceOnClickListener sourceOnClickListener) {
        this.newsSourceList = actionList;
        this.sourceOnClickListener = sourceOnClickListener;
    }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ArticleItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.article_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder holder, int position) {
        holder.bind(newsSourceList.get(position), sourceOnClickListener);
    }

    @Override
    public int getItemCount() {
        return newsSourceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final ArticleItemBinding binding;

        public ViewHolder(ArticleItemBinding articleItemBinding) {
            super(articleItemBinding.getRoot());
            this.binding = articleItemBinding;
        }

        void bind(ArticleResponseModel.Article item, SourceOnClickListener sourceOnClickListener) {
            binding.executePendingBindings();
            Glide.with(binding.getRoot()).load(item.urlToImage)
                    .thumbnail(Glide.with(binding.getRoot()).load(R.drawable.loading))
                    .into(binding.imageView);
            binding.title.setText(item.title);
            binding.time.setText(item.publishedAt);
            binding.cv.setOnClickListener(v -> {
                sourceOnClickListener.articleOnClick(item);
            });

        }
    }

    public interface SourceOnClickListener {
        void articleOnClick(ArticleResponseModel.Article article);
    }
}
