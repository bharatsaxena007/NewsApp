package com.bharat.newsapp.news.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;

import com.bharat.newsapp.R;
import com.bharat.newsapp.databinding.FragmentArticleDetailBinding;
import com.bharat.newsapp.news.model.ArticleResponseModel;
import com.bumptech.glide.Glide;


public class ArticleDetailFragment extends Fragment {

    private static final String ARTICLE = "article";
    private ArticleResponseModel.Article article;
    FragmentArticleDetailBinding mDataBinding;

    public ArticleDetailFragment() {
        // Required empty public constructor
    }

    public static ArticleDetailFragment newInstance(ArticleResponseModel.Article param1) {
        ArticleDetailFragment fragment = new ArticleDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARTICLE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*getting data through arguments*/
        if (getArguments() != null) {
            article = (ArticleResponseModel.Article) getArguments().getSerializable(ARTICLE);
        }
        ((MainActivity) getActivity()).setHeaderTitle(article.source.name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_article_detail, container, false);
        setUpData();
        /*opening web view  on read more click*/
        mDataBinding.readMore.setOnClickListener(v -> {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            String backStateName = WebViewFragment.newInstance(article.url).getClass().getSimpleName();
            ft.replace(R.id.container, WebViewFragment.newInstance(article.url), backStateName).addToBackStack(backStateName).commit();

        });
        return mDataBinding.getRoot();
    }

    // this method setup the data to the ui
    private void setUpData() {
        if (article != null) {
            Glide.with(mDataBinding.getRoot()).load(article.urlToImage)
                    .thumbnail(Glide.with(mDataBinding.getRoot()).load(R.drawable.loading))
                    .into(mDataBinding.image);
            mDataBinding.description.setText(article.content);
            mDataBinding.time.setText(article.publishedAt);
            mDataBinding.title.setText(article.title);
            mDataBinding.source.setText(article.source.name);
        }
    }
}