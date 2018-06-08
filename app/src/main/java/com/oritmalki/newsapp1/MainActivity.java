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
import android.widget.TextView;

import com.oritmalki.newsapp1.networkapi.Result;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<List<Result>> {

    private TextView titleTV;
    private List<Result> results;
    private static final int NEWS_LOADER_ID = 1;

    private static final String QUERY_ENDPOINT = "http://content.guardianapis.com/search?q=debates&api-key=" + ApiKey.getApiKey();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleTV = findViewById(R.id.title);

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
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            // Otherwise, display error
            titleTV.setText(R.string.no_connection);
        }


    }


    @NonNull
    @Override
    public Loader<List<Result>> onCreateLoader(int id, @Nullable Bundle args) {
        return new NewsLoader(this, QUERY_ENDPOINT);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Result>> loader, List<Result> data) {

        if (data != null && !data.isEmpty())
        titleTV.setText(data.get(0).getWebTitle());

    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Result>> loader) {
        titleTV.setText("");
    }
}
