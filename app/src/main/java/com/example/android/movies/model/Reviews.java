package com.example.android.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by CASA on 23/11/2017.
 */

public class Reviews implements Parcelable {
    private String author;
    private String content;

    public Reviews() {
    }

    private Reviews(Parcel in) {
        this.author = in.readString();
        this.content = in.readString();
    }

    public static final Creator<Reviews> CREATOR = new Creator<Reviews>() {
        @Override
        public Reviews createFromParcel(Parcel in) {
            return new Reviews(in);
        }

        @Override
        public Reviews[] newArray(int size) {
            return new Reviews[size];
        }
    };

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

}
