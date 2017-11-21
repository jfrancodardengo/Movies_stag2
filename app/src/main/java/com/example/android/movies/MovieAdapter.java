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
    private float scale;
    private int width,height;

    public MovieAdapter(Context context, ArrayList<Movie> movies) {
        this.context = context;
        this.movies = movies;
        //In this part it's used for adjustment the image the same device size
//        scale = context.getResources().getDisplayMetrics().density;
//        width = context.getResources().getDisplayMetrics().widthPixels - (int)(14 * scale + 0.5f);
//        height = (width / 16) * 9;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_film,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        final Movie movie = movies.get(position);

//        holder.thumbnailFilm.getLayoutParams().height = height;
//        holder.thumbnailFilm.getLayoutParams().width = width;

        Picasso.with(context).load(movie.getImage()).into(holder.thumbnailFilm);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                openDetailActivity(movie.getVoteAverage(), movie.getOriginalTitle(),
                        movie.getImage(), movie.getSynopsis(), movie.getRealeaseDate());
            }
        });

    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(dateObject);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    private void openDetailActivity(double mVoteAverage, String mOriginalTitle, String mImage, String mSynopsis, String mRealeaseDate)
    {
        Intent i=new Intent(context,DetailActivity.class);
        i.putExtra("VOTE_KEY",mVoteAverage);
        i.putExtra("TITLE_KEY",mOriginalTitle);
        i.putExtra("IMAGE_KEY",mImage);
        i.putExtra("SYNOPSIS_KEY",mSynopsis);
        i.putExtra("RELEASE_KEY",mRealeaseDate);
        context.startActivity(i);
    }

}
