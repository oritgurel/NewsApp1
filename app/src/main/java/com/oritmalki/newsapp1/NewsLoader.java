package com.oritmalki.newsapp1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.oritmalki.newsapp1.networkapi.Result;

import java.util.List;

public class NewsLoader extends AsyncTaskLoader<List<Result>> {

    private static final String LOG_TAG = NewsLoader.class.getName();
    private String mUrl;

    public NewsLoader(@NonNull Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Nullable
    @Override
    public List<Result> loadInBackground() {

        if (mUrl == null) {
            return null;
        }

        List<Result> results = QueryUtils.fetchResultData(mUrl, new INetworkListener() {
            @Override
            public void onError(String errorMessage) {
                AsyncTaskResults<Exception> asyncTaskResult = new AsyncTaskResults();
                asyncTaskResult.setError(new Exception(Exceptions.JSON_PARSE_EXCEPTION));
                return;
            }

            @Override
            public void onSuccess(List<Result> results) {
//                AsyncTaskResults
            }
        });
        return results;


    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }



}
