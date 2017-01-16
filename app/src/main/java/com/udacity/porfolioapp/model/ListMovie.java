package com.udacity.porfolioapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by clapj on 5/11/2016.
 */

public class ListMovie {

    @SerializedName("results")
    public  List<Movie> movieList;


}
