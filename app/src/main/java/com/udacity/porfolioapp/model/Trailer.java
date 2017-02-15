package com.udacity.porfolioapp.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by jhurtace on 30/01/2017.
 */
@Entity
public class Trailer implements Parcelable {

    @Id
    @SerializedName("id")
    private String id;

    @NotNull
    @Index(name = "NAME")
    @SerializedName("name")
    private String name;

    @NotNull
    @Index(name = "KEY")
    @SerializedName("key")
    private String key;


    protected Trailer(Parcel in) {
        id = in.readString();
        name = in.readString();
        key = in.readString();
    }

    @Generated(hash = 3360487)
    public Trailer(String id, @NotNull String name, @NotNull String key) {
        this.id = id;
        this.name = name;
        this.key = key;
    }

    @Generated(hash = 1801438844)
    public Trailer() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(key);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}