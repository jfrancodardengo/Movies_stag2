package com.example.android.movies.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CASA on 23/11/2017.
 */

public class Reviews implements Parcelable {
    private String author;
    private String content;

    public Reviews(String author, String content) {
        this.author = author;
        this.content = content;
    }

    public Reviews() {
    }

    public Reviews(Parcel in) {
        this.author = in.readString();
        this.content = in.readString();
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
    }

    public static final Parcelable.Creator<Reviews> CREATOR = new Parcelable.Creator<Reviews>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };
}
