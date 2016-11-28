package com.udacity.porfolioapp.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by clapj on 5/11/2016.
 */

public class Movie implements Serializable   {
    private String urlMovie;
    private String nameMovie;
    private String yearMovie;
    private String durationMovie;
    private String imageMovie;
    private String descriptionMovie;
    private List<String> listTrailerMovie;

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
