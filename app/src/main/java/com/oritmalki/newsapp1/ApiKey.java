package com.oritmalki.newsapp1;

public class ApiKey {
    private static final String apiKey = BuildConfig.MY_GUARDIAN_API_KEY;

    public static String getApiKey() {
        return apiKey;
    }
}
