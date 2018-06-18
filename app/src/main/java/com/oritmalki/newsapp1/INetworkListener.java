package com.oritmalki.newsapp1;

import com.oritmalki.newsapp1.networkapi.Result;

import java.util.List;

public interface INetworkListener {



    void onError(String errorMessage);
    void onSuccess(List<Result> results);

}
