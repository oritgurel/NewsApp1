package com.oritmalki.newsapp1;

import android.text.TextUtils;
import android.util.Log;

import com.oritmalki.newsapp1.networkapi.Result;
import com.oritmalki.newsapp1.networkapi.Tag;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving news data from Guardian API.
 */
public class QueryUtils {

    /** Tag for the log messages */
    public static final String LOG_TAG = QueryUtils.class.getName();
    private static List<Tag> tagsList;


    private QueryUtils() {

    }

    /**
     * Query the Guardian dataset and return a list of result objects.
     */

    public static AsyncTaskResults fetchResultData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the Http request.", e);
//            listener.onError("Problem making the Http request.");
              AsyncTaskResults<Exception> taskException = new AsyncTaskResults<>();
              taskException.setError(new Exception(jsonResponse.toString()));
              return taskException;
        }

        // Extract relevant fields from the JSON response and create a list of Results
        AsyncTaskResults<List<Result>> results = extractFeatureFromJson(jsonResponse);

        return results;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem with building the URL", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());

                return jsonResponse;

            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving news json data results", e);
//            listener.onError("Problem retrieving news json data results");
            AsyncTaskResults<Exception> taskException = new AsyncTaskResults<>();
            taskException.setError(e);
            return taskException.getError().getMessage();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }

        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return a list of {@link Result} objects that has been built up from
     * parsing the given JSON response.
     */

    private static AsyncTaskResults extractFeatureFromJson(String resultJson) {
        if (TextUtils.isEmpty(resultJson)) {
            return null;
        }

        List<Result> results = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.

        try {
            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(resultJson);

            //extract root object first
            JSONObject response = baseJsonResponse.getJSONObject("response");

            //extract json object Result from the baseResponse object
            JSONArray resultArray = response.getJSONArray("results");



            for (int i=0; i<resultArray.length(); i++) {
                Result result = new Result();
                JSONObject currentResult = resultArray.getJSONObject(i);
                String id = currentResult.getString("id");
                String type = currentResult.optString("type");
                String sectionId = currentResult.optString("sectionId");
                String sectionName = currentResult.optString("sectionName");
                String webPublicationDate = currentResult.optString("webPublicationDate");
                String webTitle = currentResult.optString("webTitle");
                String webUrl = currentResult.optString("webUrl");
                String apiUrl = currentResult.optString("apiUrl");
                boolean isHosted = currentResult.optBoolean("isHosted");
                String pillarId = currentResult.optString("pillarId");
                String pillarName = currentResult.optString("pillarName");
                JSONArray tags = currentResult.optJSONArray("tags");
                if (tags != null && tags.length() != 0) {
                for (int j=0; j<tags.length();j++) {
                    JSONObject currentTag = tags.optJSONObject(j);
                    String authorId = currentTag.optString("id");
                    String authorType = currentTag.optString("type");
                    String authorWebTitle = currentTag.optString("webTitle");
                    String authorUrl = currentTag.optString("webUrl");
                    String authorApiUrl = currentTag.optString("apiUrl");
                    String bio = currentTag.optString("bio");

                    tagsList = new ArrayList<>();
                    tagsList.add(new Tag(authorId, authorType, authorWebTitle, authorUrl, authorApiUrl, bio));


                    }
                    result.setTags(tagsList);
                }
                result.setId(id);
                result.setType(type);
                result.setSectionId(sectionId);
                result.setSectionName(sectionName);
                result.setWebPublicationDate(webPublicationDate);
                result.setWebTitle(webTitle);
                result.setWebUrl(webUrl);
                result.setApiUrl(apiUrl);
                result.setIsHosted(isHosted);
                result.setPillarId(pillarId);
                result.setPillarName(pillarName);

                results.add(result);

            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing JSON results");
        }
        AsyncTaskResults<List<Result>> taskResults = new AsyncTaskResults<>();
        taskResults.setResult(results);
        return taskResults;
    }

    public static String formatDate(String date) {
        org.joda.time.format.DateTimeFormatter inputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        DateTime time = inputFormatter.parseDateTime(date);
        org.joda.time.format.DateTimeFormatter outputFormatter = DateTimeFormat.forPattern("dd-MM-yy");
        return outputFormatter.print(time);

    }

    public static DateTime convertDate(String date) {
        org.joda.time.format.DateTimeFormatter inputFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        DateTime time = inputFormatter.parseDateTime(date);
        return time;
    }
}
