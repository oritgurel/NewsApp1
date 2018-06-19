package com.oritmalki.newsapp1;

public class AsyncTaskResults<T>{
    private  T result;
    private  Exception error = null;

    public AsyncTaskResults() {
    }

    public void setResult(T result) {
        this.result = result;
    }

    public void setError(Exception error) {
        this.error = error;
    }

    public T getResult() {
        return result;
    }

    public Exception getError() {
        return error;
    }

}
