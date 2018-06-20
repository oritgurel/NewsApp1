package com.oritmalki.newsapp1;

import org.json.JSONException;

public class Exceptions {

    public static final Exception JSON_PARSE_EXCEPTION = new JSONException("Problem Parsing Json.");
    public static final Exception ERROR_CODE_FROM_SERVER = new Exception("Error response code: " );

}
