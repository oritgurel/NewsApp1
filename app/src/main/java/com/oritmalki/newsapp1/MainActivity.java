package com.oritmalki.newsapp1;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.oritmalki.newsapp1.networkapi.Result;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Result>>, TextWatcher, INetworkListener {

    private TextView messageTV;
    private static TextView queryTV;
    private ImageButton searchButt;
    private static final int NEWS_LOADER_ID = 1;
    private RecyclerView newsRecyclerview;
    private NewsAdapter adapter;
    private static String queryEndpoint;
    private ProgressBar progressBar;
    private android.support.v7.widget.SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        searchView = findViewById(R.id.main_search_text);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        messageTV = findViewById(R.id.message);
//        queryTV = findViewById(R.id.main_search_text);
//        queryTV.addTextChangedListener(this);
//        searchButt = findViewById(R.id.main_search_button);
        newsRecyclerview = findViewById(R.id.news_recyclerview);
        newsRecyclerview.setVisibility(View.GONE);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        newsRecyclerview.setLayoutManager(lm);
        final Bundle bundle = new Bundle();
        queryEndpoint = "http://content.guardianapis.com/search?q=" + searchView.getQuery().toString() + "&api-key=" + ApiKey.getApiKey();
//        initLoader();


        searchView.setOnQueryTextListener(new OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                initLoader();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                queryEndpoint = "http://content.guardianapis.com/search?q=" + newText + "&api-key=" + ApiKey.getApiKey();
                return true;
            }
        });
    }


    //TODO instead of implementing onCreateLoader, replace with new task - newsLoader with queryTV.getText()...

    @NonNull
    @Override
    public Loader<List<Result>> onCreateLoader(int id, @Nullable Bundle args) {
        messageTV.setVisibility(View.GONE);
        newsRecyclerview.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        return new NewsLoader(this, queryEndpoint);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Result>> loader, List<Result> data) {

        messageTV.setText(R.string.message_no_stories_available);

        if (data != null && !data.isEmpty())
            adapter = new NewsAdapter(data);
            newsRecyclerview.setAdapter(adapter);
//            messageTV.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            newsRecyclerview.setVisibility(View.VISIBLE);

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Result>> loader) {
        adapter.clearData();
        adapter.setNews(null);
    }

    public void initLoader() {

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()) {
            // Get a reference to the LoaderManager, in order to interact with loaders.
            LoaderManager loaderManager = getSupportLoaderManager();
            // Initialize the loader. Pass in the int ID constant defined above and pass in null for
            // the bundle. Pass in this activity for the LoaderCallbacks parameter (which is valid
            // because this activity implements the LoaderCallbacks interface).
            if (loaderManager.getLoader(NEWS_LOADER_ID) == null) {
                loaderManager.initLoader(NEWS_LOADER_ID, null, this).forceLoad();
            } else {
                loaderManager.restartLoader(NEWS_LOADER_ID, null, this).forceLoad();
            }
        }else{
            // Otherwise, display error
            newsRecyclerview.setVisibility(View.GONE);
            messageTV.setVisibility(View.VISIBLE);
            messageTV.setText(R.string.no_connection);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }



    @Override
    public void onError(String errorMessage) {
//        messageTV.setVisibility(View.VISIBLE);
        messageTV.setText(errorMessage);
    }
}
