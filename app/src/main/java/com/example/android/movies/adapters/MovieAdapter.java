package com.example.android.movies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.movies.ui.DetailActivity;
import com.example.android.movies.ItemClickListener;
import com.example.android.movies.R;
import com.example.android.movies.model.Movie;
import com.example.android.movies.holders.MovieViewHolder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Guto on 15/10/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private final Context context;
    public final ArrayList<Movie> movies;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_film, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Movie movie = movies.get(position);
        Picasso.with(context).load(movie.getImage()).into(holder.thumbnailFilm);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick() {
                Intent i = new Intent(context, DetailActivity.class);
                i.putExtra("PARAM_MOVIE", movie);
                context.startActivity(i);
            }
        });

    }

    public ArrayList<Movie> getMovies() {
        return movies;
    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

}
