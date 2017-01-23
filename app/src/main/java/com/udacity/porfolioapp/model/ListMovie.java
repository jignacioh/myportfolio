package com.udacity.porfolioapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by juan
 */

public class ListMovie {

    @SerializedName("results")
    public  List<Movie> movieList;


}
