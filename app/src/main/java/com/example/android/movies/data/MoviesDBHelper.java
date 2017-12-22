package com.example.android.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CASA on 21/11/2017.
 */

class MoviesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE = String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s LONG NOT NULL, %s TEXT NOT NULL, %s DOUBLE NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL ,CONSTRAINT %s UNIQUE (%s));", MoviesContract.MoviesEntry.TABLE_NAME, MoviesContract.MoviesEntry._ID, MoviesContract.MoviesEntry.COLUMN_ID_MOVIE, MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE, MoviesContract.MoviesEntry.COLUMN_VOTE_MOVIE, MoviesContract.MoviesEntry.COLUMN_IMAGE_MOVIE, MoviesContract.MoviesEntry.COLUMN_IMAGE_BACKGROUND_MOVIE, MoviesContract.MoviesEntry.COLUMN_SYNOPSIS_MOVIE, MoviesContract.MoviesEntry.COLUMN_RELEASE_MOVIE, MoviesContract.MoviesEntry.UNIQUE_ID, MoviesContract.MoviesEntry.COLUMN_ID_MOVIE);

        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}