package com.example.android.movies.data;

import android.provider.BaseColumns;

/**
 * Created by CASA on 21/11/2017.
 */

public class MoviesContract {

    public static final class MoviesEntry implements BaseColumns {

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_NAME_MOVIE = "originalTitle";
        public static final String COLUMN_ID_MOVIE = "idMovie";
        public static final String COLUMN_VOTE_MOVIE = "voteAverage";
        public static final String COLUMN_IMAGE_MOVIE = "image";
        public static final String COLUMN_SYNOPSIS_MOVIE = "synopsis";
        public static final String COLUMN_RELEASE_MOVIE = "realeaseDate";
    }
}
