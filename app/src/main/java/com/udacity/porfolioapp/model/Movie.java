package com.udacity.porfolioapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan
 */
@Entity
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


    @Id
    @SerializedName("id")
    public Long id;

    @Property(nameInDb = ROW_POSTER)
    @SerializedName("poster_path")
    private String urlMovie;

    @Property(nameInDb = ROW_TITLE)
    @SerializedName("original_title")
    private String nameMovie;

    @Property(nameInDb = ROW_DATE)
    @SerializedName("release_date")
    private String yearMovie;

    @Property(nameInDb = ROW_POPULAR)
    @SerializedName("popularity")
    private String popularity;

    @Property(nameInDb = ROW_BACKDROP)
    @SerializedName("backdrop_path")
    private String imageMovie;

    @Property(nameInDb = ROW_OVERVIEW)
    @SerializedName("overview")
    private String descriptionMovie;

    @ToMany(referencedJoinProperty = "name")
    private List<Trailer> listTrailerMovie;

    @Property(nameInDb =ROW_VOTE)
    @NotNull
    @SerializedName("vote_count")
    private int voteCount;

    @Property(nameInDb = ROW_AVERAGE)
    @NotNull
    @SerializedName("vote_average")
    private Double voteAverage;

    public Movie() {
    }
    @Keep
    public Movie(String urlMovie, String nameMovie, String yearMovie, String durationMovie, String descriptionMovie, List<Trailer> listTrailerMovie) {
        this.urlMovie = urlMovie;
        this.nameMovie = nameMovie;
        this.yearMovie = yearMovie;
        this.descriptionMovie = descriptionMovie;
        this.listTrailerMovie = listTrailerMovie;
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
    @Keep
    public List<Trailer> getListTrailerMovie() {
        return listTrailerMovie;
    }

    public void setListTrailerMovie(List<Trailer> listTrailerMovie) {
        this.listTrailerMovie = listTrailerMovie;
    }


    protected Movie(Parcel in) {
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
    @Keep
    public Movie(Long id, String urlMovie, String nameMovie, String yearMovie, String popularity, String imageMovie, String descriptionMovie,
            int voteCount, @NotNull Double voteAverage) {
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

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 2115043241)
    public synchronized void resetListTrailerMovie() {
        listTrailerMovie = null;
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 215161401)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getMovieDao() : null;
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
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1042217376)
    private transient MovieDao myDao;





}

