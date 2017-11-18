package com.example.android.movies;

/**
 * Created by Guto on 14/10/2017.
 */

public class Movie {
    private double mVoteAverage;
    private String mOriginalTitle;
    private String mImage;
    private String mSynopsis;
    private String mRealeaseDate;

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

    public Movie(double avaliacaoVoto, String tituloOriginal, String image) {
        this.mVoteAverage = avaliacaoVoto;
        this.mOriginalTitle = tituloOriginal;
        this.mImage = image;
    }

    public Movie(){}

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
}
