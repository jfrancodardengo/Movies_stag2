package com.example.android.movies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by CASA on 21/11/2017.
 */

public class MoviesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MoviesDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " +
                MoviesContract.MoviesEntry.TABLE_NAME + " (" +
                MoviesContract.MoviesEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.MoviesEntry.COLUMN_ID_MOVIE + " LONG NOT NULL, " +
                MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE + " TEXT NOT NULL, " +
                MoviesContract.MoviesEntry.COLUMN_VOTE_MOVIE + " DOUBLE NOT NULL, " +
                MoviesContract.MoviesEntry.COLUMN_IMAGE_MOVIE + " TEXT NOT NULL, " +
                MoviesContract.MoviesEntry.COLUMN_IMAGE_BACKGROUND_MOVIE + " TEXT NOT NULL, " +
                MoviesContract.MoviesEntry.COLUMN_SYNOPSIS_MOVIE + " TEXT NOT NULL, " +
                MoviesContract.MoviesEntry.COLUMN_RELEASE_MOVIE + " TEXT NOT NULL ," +
                "CONSTRAINT " + MoviesContract.MoviesEntry.UNIQUE_ID + " UNIQUE (" + MoviesContract.MoviesEntry.COLUMN_ID_MOVIE + "));";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_NAME);
        onCreate(db);
    }
}