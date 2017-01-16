package com.udacity.porfolioapp.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by clapj on 5/11/2016.
 */

public class Movie implements Serializable   {

    @SerializedName("poster_path")
    private String urlMovie;
    @SerializedName("original_title")
    private String nameMovie;
    @SerializedName("release_date")
    private String yearMovie;
    @SerializedName("id")
    private String durationMovie;
    @SerializedName("backdrop_path")
    private String imageMovie;
    @SerializedName("overview")
    private String descriptionMovie;
    private List<String> listTrailerMovie;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("vote_average")
    private Double voteAverage;

    public Movie() {
    }

    public Movie(String urlMovie, String nameMovie, String yearMovie, String durationMovie, String descriptionMovie, List<String> listTrailerMovie) {
        this.urlMovie = urlMovie;
        this.nameMovie = nameMovie;
        this.yearMovie = yearMovie;
        this.durationMovie = durationMovie;
        this.descriptionMovie = descriptionMovie;
        this.listTrailerMovie = listTrailerMovie;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getImageMovie() {
        return imageMovie;
    }

    public void setImageMovie(String imageMovie) {
        this.imageMovie = imageMovie;
    }

    public String getDurationMovie() {
        return durationMovie;
    }

    public void setDurationMovie(String durationMovie) {
        this.durationMovie = durationMovie;
    }

    public String getUrlMovie() {
        return urlMovie;
    }

    public void setUrlMovie(String urlMovie) {
        this.urlMovie = urlMovie;
    }

    public String getNameMovie() {
        return nameMovie;
    }

    public void setNameMovie(String nameMovie) {
        this.nameMovie = nameMovie;
    }

    public String getYearMovie() {
        return yearMovie;
    }

    public void setYearMovie(String yearMovie) {
        this.yearMovie = yearMovie;
    }



    public String getDescriptionMovie() {
        return descriptionMovie;
    }

    public void setDescriptionMovie(String descriptionMovie) {
        this.descriptionMovie = descriptionMovie;
    }

    public List<String> getListTrailerMovie() {
        return listTrailerMovie;
    }

    public void setListTrailerMovie(List<String> listTrailerMovie) {
        this.listTrailerMovie = listTrailerMovie;
    }
}
