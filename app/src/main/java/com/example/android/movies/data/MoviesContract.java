package com.example.android.movies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by CASA on 21/11/2017.
 */

public class MoviesContract {

    // The authority, which is how your code knows which Content Provider to access
    public static final String AUTHORITY = "com.example.android.movies";

    // The base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Define the possible paths for accessing data in this contract
    // This is the path for the "tasks" directory
    public static final String PATH_MOVIES = "movies";


    public static final class MoviesEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIES).build();


        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_ID_MOVIE = "idMovie";
        public static final String COLUMN_NAME_MOVIE = "originalTitle";
        public static final String COLUMN_VOTE_MOVIE = "voteAverage";
        public static final String COLUMN_IMAGE_MOVIE = "image";
        public static final String COLUMN_SYNOPSIS_MOVIE = "synopsis";
        public static final String COLUMN_RELEASE_MOVIE = "realeaseDate";
    }
}
