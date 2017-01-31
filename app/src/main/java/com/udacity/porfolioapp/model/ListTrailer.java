package com.udacity.porfolioapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jhurtace on 30/01/2017.
 */
public class ListTrailer {

    @SerializedName("results")
    public List<Trailer> trailerList;
}
