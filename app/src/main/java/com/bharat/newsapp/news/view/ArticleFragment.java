package com.bharat.newsapp.news.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bharat.newsapp.BuildConfig;
import com.bharat.newsapp.R;
import com.bharat.newsapp.databinding.FragmentArticleBinding;
import com.bharat.newsapp.news.model.ArticleAdapter;
import com.bharat.newsapp.news.model.ArticleResponseModel;
import com.bharat.newsapp.news.network.Api;
import com.bharat.newsapp.news.network.ApiInterface;
import com.bharat.newsapp.news.repo.NewsRepository;
import com.bharat.newsapp.news.viewmodel.NewsViewModel;
import com.bharat.newsapp.news.viewmodel.NewsViewModelFactory;

import java.util.ArrayList;


public class ArticleFragment extends Fragment implements ArticleAdapter.SourceOnClickListener {

    private static final String SOURCE_NAME = "sourceName";
    private String sourceName;
    FragmentArticleBinding mDataBinding;
    NewsViewModel newsViewModel;
    ApiInterface apiInterface;
    ArrayList<ArticleResponseModel.Article> tempArticleList = new ArrayList<>();
    ArrayList<ArticleResponseModel.Article> articleList = new ArrayList<>();
    ArticleAdapter mAdapter;


    public static ArticleFragment newInstance(String sourceName) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(SOURCE_NAME, sourceName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sourceName = getArguments().getString(SOURCE_NAME);
        }
        ((MainActivity) getActivity()).setHeaderTitle(sourceName);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity) getActivity()).getSupportActionBar().show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // initializing binding object
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_article, container, false);
        newsViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity(), new NewsViewModelFactory(new NewsRepository(apiInterface))).get(NewsViewModel.class);
        mDataBinding.setViewModel(newsViewModel);
        mDataBinding.shimmer.startShimmer();
        setHasOptionsMenu(false);
        setSearchIcon();
        apiInterface = Api.getClient().create(ApiInterface.class);

        //api call for getting the news detail
        newsViewModel.callArticleApi(BuildConfig.NewsAPIKEY, sourceName);
        newsViewModel.articleResponse.observe((LifecycleOwner) getActivity(), this::onArticleResponse);
        return mDataBinding.getRoot();
    }

    private void setSearchIcon() {
        Toolbar toolbar = mDataBinding.fragmentHeader.toolbar;
        toolbar.inflateMenu(R.menu.search_menu);
        MenuItem item = toolbar.getMenu().findItem(R.id.action_search);
        if (((MainActivity) (getActivity()) != null && ((MainActivity) (getActivity())).getSupportActionBar() != null &&
                ((MainActivity) (getActivity())).getSupportActionBar().getThemedContext() != null)) {
            //setting up the search view for searching the news
            androidx.appcompat.widget.SearchView searchView = new androidx.appcompat.widget.SearchView(((MainActivity) (getActivity())).getSupportActionBar().getThemedContext());
            androidx.appcompat.widget.SearchView.SearchAutoComplete theTextArea = searchView.findViewById(androidx.appcompat.R.id.search_src_text);
            ImageView icon = searchView.findViewById(androidx.appcompat.R.id.search_close_btn);
            icon.setImageResource(R.drawable.ic_baseline_close_24);
            theTextArea.setTextColor(Color.WHITE);
            theTextArea.setHint("Search title");
            theTextArea.setHintTextColor(Color.WHITE);
            MenuItemCompat.setShowAsAction(item, MenuItemCompat.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItemCompat.SHOW_AS_ACTION_IF_ROOM);
            MenuItemCompat.setActionView(item, searchView);
            searchView.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    search(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    search(newText);
                    mDataBinding.progressBar.progressBarMain.setVisibility(View.GONE);
                    return false;
                }
            });


        }

    }

    private void search(String search) {
        if (search.length() > 2 && tempArticleList != null && articleList != null && articleList.size() > 0) {
            tempArticleList.clear();
            for (ArticleResponseModel.Article article : articleList) {
                if (article.title.toLowerCase().contains(search.toLowerCase()))
                    tempArticleList.add(article);
            }
            updateRecyclerView(tempArticleList);
        }
        if (search.length() == 0)
            updateRecyclerView(articleList);
    }

    private void updateRecyclerView(ArrayList<ArticleResponseModel.Article> article) {
        mAdapter.newsSourceList = article;
        mAdapter.notifyDataSetChanged();
        mDataBinding.rvList.setAdapter(mAdapter);
        newsViewModel.mIsLoading.setValue(false);
    }

    private void onArticleResponse(ArticleResponseModel articleResponseModel) {
        mDataBinding.shimmer.stopShimmer();
        mDataBinding.shimmer.setVisibility(View.GONE);
        if (isAdded() && getActivity() != null && articleResponseModel != null) {
            articleList.clear();
            tempArticleList.clear();
            articleList.addAll(articleResponseModel.articlesList);
            tempArticleList.addAll(articleResponseModel.articlesList);
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mDataBinding.rvList.setLayoutManager(mLayoutManager);
            mAdapter = new ArticleAdapter(tempArticleList, this);
            mDataBinding.rvList.setAdapter(mAdapter);
            mAdapter.notifyDataSetChanged();
        }
    }


    @Override
    public void articleOnClick(ArticleResponseModel.Article name) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String backStateName = ArticleDetailFragment.newInstance(name).getClass().getSimpleName();
        ft.replace(R.id.container, ArticleDetailFragment.newInstance(name), backStateName).addToBackStack(backStateName).commit();

    }
}