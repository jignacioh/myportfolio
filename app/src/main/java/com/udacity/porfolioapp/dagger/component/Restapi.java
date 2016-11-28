package com.udacity.porfolioapp.dagger.component;

import com.udacity.porfolioapp.model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by clapj on 3/11/2016.
 */

public interface Restapi {
    @GET("/rest/MovieResponse.php")
    Call<List<Movie>> getPosts();
}
