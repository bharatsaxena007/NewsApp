package com.bharat.newsapp.news.view;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.databinding.DataBindingUtil;

import com.bharat.newsapp.R;
import com.bharat.newsapp.databinding.FragmentWebViewBinding;

public class WebViewFragment extends Fragment {


    private static final String URL = "url";

    private String url;
    FragmentWebViewBinding mDataBinding;


    public WebViewFragment() {
        // Required empty public constructor
    }

    public static WebViewFragment newInstance(String param1) {
        WebViewFragment fragment = new WebViewFragment();
        Bundle args = new Bundle();
        args.putString(URL, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            url = getArguments().getString(URL);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mDataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_view, container, false);
        mDataBinding.webView.getSettings().setJavaScriptEnabled(true);
        mDataBinding.loader.setVisibility(View.VISIBLE);
        mDataBinding.webView.getSettings().setDomStorageEnabled(true);
        mDataBinding.webView.loadUrl(url);
        mDataBinding.webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                mDataBinding.loader.setVisibility(View.GONE);

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String request) {

                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        return mDataBinding.getRoot();
    }
}