package com.udacity.porfolioapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by jhurtace on 30/01/2017.
 */
public class Trailer implements Parcelable {


    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("key")
    private String key;

    protected Trailer(Parcel in) {
        id = in.readString();
        name = in.readString();
        key = in.readString();
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
}