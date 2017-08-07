/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package nacoda.android.com.popularmovies.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    private final static String URL_POPULAR =
            "https://api.themoviedb.org/3/movie/popular";
    private final static String URL_TOP_RATED =
            "https://api.themoviedb.org/3/movie/top_rated";

    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/w185/";

    private final static String PARAM_API_KEY = "api_key";
    private final static String apiKey = "d1fc10c2bd3bb72bd5ddf8f58a74a1a3";

    private final static String PARAM_LANGUAGE = "language";
    private final static String languageParam = "en-US";

    /*Membuat Url*/
    public static URL popularUrl() {

//        Menggunakan URL_POPULAR sebagai URL Utama

        Uri builtUri = Uri.parse(URL_POPULAR).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, apiKey)
                .appendQueryParameter(PARAM_LANGUAGE, languageParam)
                .build();

        // Mengkonversi URL tadi menjadi string agar bisa kita pakai di dalam MovieAsyncTask
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL topRatedUrl() {

//        Menggunakan URL_POPULAR sebagai URL Utama

        Uri builtUri = Uri.parse(URL_TOP_RATED).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, apiKey)
                .appendQueryParameter(PARAM_LANGUAGE, languageParam)
                .build();

        // Mengkonversi URL tadi menjadi string agar bisa kita pakai di dalam MovieAsyncTask
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL trailersUrl(String id) {

        String URL_TRAILERS = "http://api.themoviedb.org/3/movie/" + id + "/videos";

        Uri builtUri = Uri.parse(URL_TRAILERS).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, apiKey)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    public static URL reviewsUrl(String id){
        String URL_REVIEWS  = "http://api.themoviedb.org/3/movie/"+ id +"/reviews";

        Uri builtUri = Uri.parse(URL_REVIEWS).buildUpon()
                .appendQueryParameter(PARAM_API_KEY, apiKey)
                .build();

        URL url = null;
        try{
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e){
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

        urlConnection.setConnectTimeout(5000);
        urlConnection.setReadTimeout(5000);

        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}