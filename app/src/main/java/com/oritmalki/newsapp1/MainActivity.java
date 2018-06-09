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
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.oritmalki.newsapp1.networkapi.Result;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Result>> {

    private TextView titleTV;
    private static TextView queryTV;
    private ImageButton searchButt;
    private static final int NEWS_LOADER_ID = 1;
    private RecyclerView newsRecyclerview;
    private NewsAdapter adapter;
    private static String queryEndpoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        titleTV = findViewById(R.id.title);
        queryTV = findViewById(R.id.main_search_text);
        searchButt = findViewById(R.id.main_search_button);
        newsRecyclerview = findViewById(R.id.news_recyclerview);
        LinearLayoutManager lm = new LinearLayoutManager(this);
        newsRecyclerview.setLayoutManager(lm);
        final Bundle bundle = new Bundle();
        queryEndpoint = "http://content.guardianapis.com/search?q=" + setQueryText() + "&api-key=" + ApiKey.getApiKey();
//        initLoader();




        searchButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    initLoader();

            }
        });

    }


    //TODO instead of implementing onCreateLoader, replace with new task - newsLoader with queryTV.getText()...

    @NonNull
    @Override
    public Loader<List<Result>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsLoader(this, queryEndpoint);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Result>> loader, List<Result> data) {

        if (data != null && !data.isEmpty())
            adapter = new NewsAdapter(data);
            newsRecyclerview.setAdapter(adapter);
            newsRecyclerview.setVisibility(View.VISIBLE);
//        titleTV.setText(data.get(0).getWebTitle());

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Result>> loader) {
        adapter.clearData();
//        initLoader();
//        titleTV.setText("");
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
                loaderManager.initLoader(NEWS_LOADER_ID, null, this);
            } else {
                loaderManager.restartLoader(NEWS_LOADER_ID, null, this);
            }
        }else{
            // Otherwise, display error
            newsRecyclerview.setVisibility(View.GONE);
            titleTV.setText(R.string.no_connection);
        }
    }

    public static String setQueryText() {
        return queryTV.getText().toString();
    }
}
