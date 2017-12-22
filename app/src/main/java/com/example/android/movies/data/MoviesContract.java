package com.example.android.movies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by CASA on 21/11/2017.
 */

public class MoviesContract {

    public static final String AUTHORITY = "com.example.android.movies";
    private static final Uri BASE_CONTENT_URI = Uri.parse(String.format("content://%s", AUTHORITY));
    public static final String PATH_MOVIES = "movies";

    public static final class MoviesEntry implements BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();
        public static final String TABLE_NAME = "movies";
        public static final String UNIQUE_ID = "id_movie_unique";
        public static final String COLUMN_ID_MOVIE = "idMovie";
        public static final String COLUMN_NAME_MOVIE = "originalTitle";
        public static final String COLUMN_VOTE_MOVIE = "voteAverage";
        public static final String COLUMN_IMAGE_MOVIE = "image";
        public static final String COLUMN_IMAGE_BACKGROUND_MOVIE = "imageBackground";
        public static final String COLUMN_SYNOPSIS_MOVIE = "synopsis";
        public static final String COLUMN_RELEASE_MOVIE = "realeaseDate";
    }
}
