package com.udacity.porfolioapp.model;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan
 */
public class Movie  implements Parcelable   {

    public static final String ROW_ID="id";
    public static final String ROW_POSTER="POSTER";
    public static final String ROW_TITLE="TITLE";
    public static final String ROW_DATE="DATE";
    public static final String ROW_POPULAR="POPULAR";
    public static final String ROW_BACKDROP="BACKDROP";
    public static final String ROW_OVERVIEW="OVERVIEW";
    public static final String ROW_VOTE="VOTE";
    public static final String ROW_AVERAGE="AVERAGE";
    public static final String TABLE_NAME = "movie";


    @SerializedName("id")
    public Long id;

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

    private List<Trailer> listTrailerMovie;

    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("vote_average")
    private Double voteAverage;

    public Movie() {
    }
    public Movie(String urlMovie, String nameMovie, String yearMovie, String durationMovie, String descriptionMovie, List<Trailer> listTrailerMovie) {
        this.urlMovie = urlMovie;
        this.nameMovie = nameMovie;
        this.yearMovie = yearMovie;
        this.descriptionMovie = descriptionMovie;
        this.listTrailerMovie = listTrailerMovie;
    }

    public Movie(Cursor cursor) {
        this.id = cursor.getLong(cursor.getColumnIndex(MovieContract.MovieEntry._ID));
        this.nameMovie =cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
        this.yearMovie = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_DATE));
        this.descriptionMovie = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
        this.urlMovie = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER));
        this.imageMovie = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_BACKDROP));
        this.popularity = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POPULAR));
        this.voteAverage =cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_AVERAGE));
        this.voteCount = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE));
    }

    public static final class MovieEntry implements BaseColumns{
        public static final String COLUMN_ID="id";
        public static final String COLUMN_POSTER="POSTER";
        public static final String COLUMN_TITLE="TITLE";
        public static final String COLUMN_DATE="DATE";
        public static final String COLUMN_POPULAR="POPULAR";
        public static final String COLUMN_BACKDROP="BACKDROP";
        public static final String COLUMN_OVERVIEW="OVERVIEW";
        public static final String COLUMN_VOTE="VOTE";
        public static final String COLUMN_AVERAGE="AVERAGE";


    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
    public List<Trailer> getListTrailerMovie() {
        return listTrailerMovie;
    }

    public void setListTrailerMovie(List<Trailer> listTrailerMovie) {
        this.listTrailerMovie = listTrailerMovie;
    }


    public Movie(Parcel in) {
        id = in.readLong();
        urlMovie = in.readString();
        nameMovie = in.readString();
        yearMovie = in.readString();
        popularity = in.readString();
        imageMovie = in.readString();
        descriptionMovie = in.readString();
        if (in.readByte() == 0x01) {
            listTrailerMovie = new ArrayList<Trailer>();
            in.readList(listTrailerMovie, Trailer.class.getClassLoader());
        } else {
            listTrailerMovie = null;
        }
        voteCount = in.readInt();
        voteAverage = in.readByte() == 0x00 ? null : in.readDouble();
    }
    public Movie(Long id, String urlMovie, String nameMovie, String yearMovie, String popularity, String imageMovie, String descriptionMovie,
            int voteCount,  Double voteAverage) {
        this.id = id;
        this.urlMovie = urlMovie;
        this.nameMovie = nameMovie;
        this.yearMovie = yearMovie;
        this.popularity = popularity;
        this.imageMovie = imageMovie;
        this.descriptionMovie = descriptionMovie;
        this.voteCount = voteCount;
        this.voteAverage = voteAverage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
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

