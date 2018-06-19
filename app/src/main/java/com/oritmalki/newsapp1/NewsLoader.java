package com.oritmalki.newsapp1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class NewsLoader extends AsyncTaskLoader<AsyncTaskResults> {

    private static final String LOG_TAG = NewsLoader.class.getName();
    private String mUrl;

    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Nullable
    @Override
    public AsyncTaskResults loadInBackground() {

        if (mUrl == null) {
            return null;
        }
        AsyncTaskResults asyncTaskResult = new AsyncTaskResults<>();
        AsyncTaskResults results = QueryUtils.fetchResultData(mUrl);
        asyncTaskResult.setResult(results);

        return results;
//        return results;


    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }



}
