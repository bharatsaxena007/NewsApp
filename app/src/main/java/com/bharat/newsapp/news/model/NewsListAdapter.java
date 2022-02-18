package com.bharat.newsapp.news.model;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bharat.newsapp.R;
import com.bharat.newsapp.databinding.NewsListItemBinding;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.ViewHolder> {

    private final ArrayList<SourceResponse.SourceItem> newsSourceList;
    SourceOnClickListener sourceOnClickListener;

    public NewsListAdapter(ArrayList<SourceResponse.SourceItem> actionList, SourceOnClickListener sourceOnClickListener) {
        this.newsSourceList = actionList;
        this.sourceOnClickListener = sourceOnClickListener;
    }

    @NonNull
    @Override
    public NewsListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsListItemBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                R.layout.news_list_item, parent, false);

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(NewsListAdapter.ViewHolder holder, int position) {
        holder.bind(newsSourceList.get(position), sourceOnClickListener);
    }

    @Override
    public int getItemCount() {
        return newsSourceList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final NewsListItemBinding binding;

        public ViewHolder(NewsListItemBinding newsListItemBinding) {
            super(newsListItemBinding.getRoot());
            this.binding = newsListItemBinding;
        }

        void bind(SourceResponse.SourceItem item, SourceOnClickListener sourceOnClickListener) {
            binding.executePendingBindings();
            binding.tvSource.setText(item.name);
            binding.tvSourceDescription.setText(item.description);
            binding.cvMain.setOnClickListener(v -> {
                sourceOnClickListener.sourceOnClick(item.id);
            });
        }
    }

    public interface SourceOnClickListener {
        void sourceOnClick(String name);
    }
}
