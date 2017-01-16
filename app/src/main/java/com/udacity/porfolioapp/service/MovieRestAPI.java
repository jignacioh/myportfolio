package com.udacity.porfolioapp.service;

import com.udacity.porfolioapp.model.ListMovie;
import com.udacity.porfolioapp.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by clapj on 5/11/2016.
 */

public interface MovieRestAPI {
    @GET("/rest/MovieResponse.php")
    Call<ListMovie> loadMovies();

    @GET("movie/top_rated")
    Call<ListMovie> loadAllMovies(@Query("api_key") String apiKey);
}
