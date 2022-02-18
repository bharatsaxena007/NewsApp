package com.bharat.newsapp.news.view;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.bharat.newsapp.R;
import com.bharat.newsapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpToolbar();
        loadSourceFragment();
    }

    private void loadSourceFragment() {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        String backStateName = SourceFragment.newInstance().getClass().getSimpleName();
        ft.add(R.id.container, SourceFragment.newInstance(), backStateName).addToBackStack(backStateName).commit();

    }

    protected void setUpToolbar() {
        setSupportActionBar(binding.toolbar.toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            // Hide the original Title of the Header
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    public void setHeaderTitle(String title) {
        if (title == null)
            return;
        else if (TextUtils.isEmpty(title)) {
            binding.toolbar.tvTitle.setText("");
            return;
        }
        binding.toolbar.tvTitle.setText(title);
    }

}