package com.udacity.porfolioapp.service;

import com.udacity.porfolioapp.model.ListMovie;
import com.udacity.porfolioapp.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by clapj on 5/11/2016.
 */

public interface MovieRestAPI {
    @GET("/rest/MovieResponse.php")
    Call<ListMovie> loadMovies();
}
