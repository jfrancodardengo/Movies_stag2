package com.example.android.movies.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Guto on 14/10/2017.
 */

public class Movie implements Parcelable {
    private int mMovieId;
    private double mVoteAverage;
    private String mOriginalTitle;
    private String mImage;
    private String mImageBack;
    private String mSynopsis;
    private String mRealeaseDate;

    public Movie() {
    }

    private Movie(Parcel in) {
        mMovieId = in.readInt();
        mVoteAverage = in.readDouble();
        mOriginalTitle = in.readString();
        mImage = in.readString();
        mImageBack = in.readString();
        mSynopsis = in.readString();
        mRealeaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(double mVoteAverage) {
        this.mVoteAverage = mVoteAverage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String mOriginalTitle) {
        this.mOriginalTitle = mOriginalTitle;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public String getSynopsis() {
        return mSynopsis;
    }

    public void setSynopsis(String mSynopsis) {
        this.mSynopsis = mSynopsis;
    }

    public String getRealeaseDate() {
        return mRealeaseDate;
    }

    public void setRealeaseDate(String mRealeaseDate) {
        this.mRealeaseDate = mRealeaseDate;
    }

    public int getMovieId() {
        return mMovieId;
    }

    public void setMovieId(int mMovieId) {
        this.mMovieId = mMovieId;
    }

    public String getImageBack() {
        return mImageBack;
    }

    public void setImageBack(String mImageBack) {
        this.mImageBack = mImageBack;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mMovieId);
        dest.writeDouble(mVoteAverage);
        dest.writeString(mOriginalTitle);
        dest.writeString(mImage);
        dest.writeString(mImageBack);
        dest.writeString(mSynopsis);
        dest.writeString(mRealeaseDate);
    }
}
