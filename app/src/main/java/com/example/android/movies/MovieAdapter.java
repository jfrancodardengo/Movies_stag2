package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Guto on 15/10/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private Context context;
    private ArrayList<Movie> movies;


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

//        holder.setItemClickListener(new ItemClickListener() {
//            @Override
//            public void onItemClick(int position) {
//                Intent i = new Intent(context, DetailActivity.class);
//                i.putExtra("movie", movie);
//                context.startActivity(i);
//            }
//        });


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(context, DetalheActivity.class);
                i.putExtra("movie", movie);
                context.startActivity(i);
            }
        });

    }


    @Override
    public int getItemCount() {
        return movies.size();
    }

}
