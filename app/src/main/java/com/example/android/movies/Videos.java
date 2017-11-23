package com.example.android.movies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CASA on 23/11/2017.
 */

public class Videos implements Parcelable {
    private String key;
    private String name;

    public Videos(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public Videos(Parcel in) {
        this.key = in.readString();
        this.name = in.readString();
    }

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

    public static final Parcelable.Creator<Videos> CREATOR = new Parcelable.Creator<Videos>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Videos createFromParcel(Parcel in) {
            return new Videos(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Videos[] newArray(int size) {
            return new Videos[size];
        }
    };
}
