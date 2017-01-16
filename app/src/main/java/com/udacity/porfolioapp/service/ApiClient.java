package com.udacity.porfolioapp.service;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jhurtace on 30/11/2016.
 */
public class ApiClient {
    public static final String TAG_API="b3420c7e4ccc91fd03c3cd0ff60d9a92";
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;

    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)

                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
    private static OkHttpClient getRequestHeader() {
         OkHttpClient httpClient = new OkHttpClient();
         //httpClient.setConnectTimeout(20, TimeUnit.SECONDS);
         //httpClient.setReadTimeout(30, TimeUnit.SECONDS);
        // Log.i(TAG_API,"GET CUSTOM OK HTTP CLIENT");
        return httpClient;
    }
    
}
