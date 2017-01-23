package com.udacity.porfolioapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan
 */

public class Movie implements Parcelable   {

    @SerializedName("poster_path")
    private String urlMovie;
    @SerializedName("original_title")
    private String nameMovie;
    @SerializedName("release_date")
    private String yearMovie;
    @SerializedName("popularity")
    private String popularity;
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

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
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


    protected Movie(Parcel in) {
        urlMovie = in.readString();
        nameMovie = in.readString();
        yearMovie = in.readString();
        popularity = in.readString();
        imageMovie = in.readString();
        descriptionMovie = in.readString();
        if (in.readByte() == 0x01) {
            listTrailerMovie = new ArrayList<String>();
            in.readList(listTrailerMovie, String.class.getClassLoader());
        } else {
            listTrailerMovie = null;
        }
        voteCount = in.readInt();
        voteAverage = in.readByte() == 0x00 ? null : in.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(urlMovie);
        dest.writeString(nameMovie);
        dest.writeString(yearMovie);
        dest.writeString(popularity);
        dest.writeString(imageMovie);
        dest.writeString(descriptionMovie);
        if (listTrailerMovie == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(listTrailerMovie);
        }
        dest.writeInt(voteCount);
        if (voteAverage == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeDouble(voteAverage);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}

