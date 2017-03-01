package com.udacity.porfolioapp.service;

import com.udacity.porfolioapp.model.ListMovie;
import com.udacity.porfolioapp.model.ListReview;
import com.udacity.porfolioapp.model.ListTrailer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Juan PC
 */

public interface MovieRestAPI {
    //@GET("discover/movie")
    //Call<ListMovie> loadAllMovies(@Query("api_key") String apiKey);
    @GET("movie/popular")
    Call<ListMovie> loadMostPopularMovies(@Query("api_key") String apiKey);
    @GET("movie/top_rated")
    Call<ListMovie> loadHighRatedMovies(@Query("api_key") String apiKey);
    @GET("movie/{movieId}/videos")
    Call<ListTrailer> loadTrailersMovie(@Path("movieId") String movieId, @Query("api_key") String apiKey);
    @GET("movie/{movieId}/reviews")
    Call<ListReview> loadReviewsMovie(@Path("movieId") String movieId, @Query("api_key") String apiKey);
}
