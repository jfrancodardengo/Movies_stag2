package com.example.android.movies;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.movies.data.MoviesContract;
import com.squareup.picasso.Picasso;

/**
 * Created by CASA on 10/12/2017.
 */

public class OverviewAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    Context context;
    private Movie movie;

    public OverviewAdapter(Context context, Movie movie) {
        this.context = context;
        this.movie = movie;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_overview, parent, false));
    }

    @Override
    public void onBindViewHolder(MovieViewHolder vh, int position) {
        if (movie != null) {
            vh.title.setText(movie.getOriginalTitle());
            vh.vote.setText(String.valueOf(movie.getVoteAverage()));
            vh.release.setText(movie.getRealeaseDate());
            vh.synopsis.setText(movie.getSynopsis());

            Picasso.with(context).load(movie.getImage()).into(vh.thumbnailDetailCard);

//            vh.fabButton.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //new content values
//                    ContentValues contentValues = new ContentValues();
//                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_ID_MOVIE, movie.getMovieId());
//                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE, movie.getOriginalTitle());
//                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_MOVIE, movie.getVoteAverage());
//                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_IMAGE_MOVIE, movie.getImage());
//                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_IMAGE_BACKGROUND_MOVIE, movie.getImageBack());
//                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_SYNOPSIS_MOVIE, movie.getSynopsis());
//                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_RELEASE_MOVIE, movie.getRealeaseDate());
//
//                    Uri uri = context.getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, contentValues);
//
//                    int idMovie = movie.getMovieId();
//
//
//                    if (uri != null) {
////                        context.getContentResolver().update(uri,contentValues, MoviesContract.MoviesEntry.COLUMN_ID_MOVIE,new String[]{String.valueOf(idMovie)});
//                        Toast.makeText(context,"Filme " + movie.getOriginalTitle().toString() +" favoritado!", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
