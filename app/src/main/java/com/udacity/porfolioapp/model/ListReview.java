package com.udacity.porfolioapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jhurtace on 01/03/2017.
 */
public class ListReview {
    @SerializedName("results")
    public List<Review> reviewList;
}
