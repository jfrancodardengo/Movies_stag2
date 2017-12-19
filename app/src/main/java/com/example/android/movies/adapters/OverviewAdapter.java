package com.example.android.movies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.movies.R;
import com.example.android.movies.model.Movie;
import com.example.android.movies.holders.MovieViewHolder;
import com.squareup.picasso.Picasso;

/**
 * Created by CASA on 10/12/2017.
 */

public class OverviewAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private Context context;
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

        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
