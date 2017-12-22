package com.example.android.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CASA on 23/11/2017.
 */

public class Videos implements Parcelable {
    private String key;
    private String name;

    public Videos() {
    }

    private Videos(Parcel in) {
        this.key = in.readString();
        this.name = in.readString();
    }

    public static final Creator<Videos> CREATOR = new Creator<Videos>() {
        @Override
        public Videos createFromParcel(Parcel in) {
            return new Videos(in);
        }

        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(key);
        dest.writeString(name);
    }

}
