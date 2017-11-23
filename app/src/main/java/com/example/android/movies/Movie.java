package com.example.android.movies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Guto on 14/10/2017.
 */

public class Movie implements Parcelable{
    private double mVoteAverage;
    private String mOriginalTitle;
    private String mImage;
    private String mSynopsis;
    private String mRealeaseDate;

    public Movie(){}

    /**
     * Constrói um novo objeto {@link Movie}.
     *
     * @param avaliacaoVoto é a quantidade de votos avaliados
     * @param tituloOriginal é o título original
     * @param imagem é o código que identifica a imagem
     * @param sinopse é a mSynopsis do filme
     * @param dataLancamento é a data em que foi lançado o filme
     */

    public Movie(double avaliacaoVoto, String tituloOriginal, String imagem, String sinopse, String dataLancamento) {
        this.mVoteAverage = avaliacaoVoto;
        this.mOriginalTitle = tituloOriginal;
        this.mImage = imagem;
        this.mSynopsis = sinopse;
        this.mRealeaseDate = dataLancamento;
    }

    /* Using the `in` variable, we can retrieve the values that
     we originally wrote into the `Parcel`.  This constructor is usually
    private so that only the `CREATOR` field can access.*/
    public Movie(Parcel in){
        this.mVoteAverage = in.readDouble();
        this.mOriginalTitle = in.readString();
        this.mImage = in.readString();
        this.mSynopsis = in.readString();
        this.mRealeaseDate = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {

        // This simply calls our new constructor (typically private) and
        // passes along the unmarshalled `Parcel`, and then returns the new object!
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        // We just need to copy this and change the type to match our class.
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(double avaliacaoVoto, String tituloOriginal, String image) {
        this.mVoteAverage = avaliacaoVoto;
        this.mOriginalTitle = tituloOriginal;
        this.mImage = image;
    }


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

    @Override
    public int describeContents() {
        return 0;
    }

    /* This is where you write the values you want to save to the `Parcel`.
     The `Parcel` class has methods defined to help you save all of your values.
    Note that there are only methods defined for simple values, lists, and other Parcelable objects.
    You may need to make several classes Parcelable to send the data you want.*/
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(mVoteAverage);
        dest.writeString(mOriginalTitle);
        dest.writeString(mImage);
        dest.writeString(mSynopsis);
        dest.writeString(mRealeaseDate);
    }
}
