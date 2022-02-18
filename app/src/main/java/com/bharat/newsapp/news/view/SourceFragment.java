package com.bharat.newsapp.news.view;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bharat.newsapp.BuildConfig;
import com.bharat.newsapp.R;
import com.bharat.newsapp.databinding.FragmentSourceBinding;
import com.bharat.newsapp.news.model.NewsListAdapter;
import com.bharat.newsapp.news.model.SourceResponse;
import com.bharat.newsapp.news.network.Api;
import com.bharat.newsapp.news.network.ApiInterface;
import com.bharat.newsapp.news.repo.NewsRepository;
import com.bharat.newsapp.news.viewmodel.NewsViewModel;
import com.bharat.newsapp.news.viewmodel.NewsViewModelFactory;

public class SourceFragment extends Fragment implements NewsListAdapter.SourceOnClickListener {

    FragmentSourceBinding mDataBinding;
    NewsViewModel newsViewModel;
    ApiInterface apiInterface;

    public static SourceFragment newInstance() {
        SourceFragment fragment = new SourceFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDataBinding = (FragmentSourceBinding) DataBindingUtil.inflate(inflater, R.layout.fragment_source, container, false);
        mDataBinding.shimmer.showShimmer(true);
        apiInterface = Api.getClient().create(ApiInterface.class);
        newsViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity(), new NewsViewModelFactory(new NewsRepository(apiInterface))).get(NewsViewModel.class);
        mDataBinding.setViewModel(newsViewModel);
        ((MainActivity) getActivity()).setHeaderTitle(getString(R.string.sources));
        newsViewModel.callNewsSourceApi(BuildConfig.NewsAPIKEY);
        newsViewModel.newsListResponse.observe((LifecycleOwner) getActivity(), this::setupRecyclerView);
        return mDataBinding.getRoot();
    }


    protected void setupRecyclerView(SourceResponse sourceResponse) {
        mDataBinding.shimmer.stopShimmer();
        mDataBinding.shimmer.setVisibility(View.GONE);
        mDataBinding.progress.progressBarMain.setVisibility(View.GONE);
        if (isAdded() && getActivity() != null && sourceResponse != null) {
            LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
            mDataBinding.rvList.setLayoutManager(mLayoutManager);
            NewsListAdapter mAdapter = new NewsListAdapter(sourceResponse.sourceItems, this);
            mDataBinding.rvList.setAdapter(mAdapter);
        }


    }

    @Override
    public void sourceOnClick(String name) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String backStateName = ArticleFragment.newInstance(name).getClass().getSimpleName();
        ft.replace(R.id.container, ArticleFragment.newInstance(name), backStateName).addToBackStack(backStateName).commit();

    }

}