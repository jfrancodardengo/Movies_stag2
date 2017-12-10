package com.example.android.movies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.movies.data.MoviesContract;
import com.squareup.picasso.Picasso;

/**
 * Created by CASA on 10/12/2017.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder>{
    private Movie movie;
    private Context context;

    MovieViewHolder movieViewHolder;

    public DetailAdapter(Context context,Movie movie) {
        this.movie = movie;
        this.context = context;
    }

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_detalhe, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        if(movie != null){
            Picasso.with(context).load(movie.getImageBack()).into(holder.thumbnailDetail);

            movieViewHolder.title.setText(movie.getOriginalTitle());
            movieViewHolder.vote.setText(String.valueOf(movie.getVoteAverage()));
            movieViewHolder.release.setText(movie.getRealeaseDate());
            movieViewHolder.synopsis.setText(movie.getSynopsis());

            holder.fabButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //new content values
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_ID_MOVIE, movie.getMovieId());
                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE, movie.getOriginalTitle());
                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_MOVIE, movie.getVoteAverage());
                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_IMAGE_MOVIE, movie.getImage());
                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_IMAGE_BACKGROUND_MOVIE, movie.getImageBack());
                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_SYNOPSIS_MOVIE, movie.getSynopsis());
                    contentValues.put(MoviesContract.MoviesEntry.COLUMN_RELEASE_MOVIE, movie.getRealeaseDate());

                    Uri uri = context.getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, contentValues);

                    if (uri != null) {
//                        context.getContentResolver().update(uri,contentValues, MoviesContract.MoviesEntry.COLUMN_ID_MOVIE,new String[]{String.valueOf(idMovie)});
                        Toast.makeText(context,"Filme " + movie.getOriginalTitle().toString() +" favoritado!", Toast.LENGTH_LONG).show();
                    }
                }
            });

            holder.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent i = new Intent(context, DetalheActivity.class);
                    i.putExtra("movie", movie);
                    context.startActivity(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView thumbnailDetail;
        FloatingActionButton fabButton;

        ItemClickListener itemClickListener;


        public DetailViewHolder(View view) {
            super(view);

            thumbnailDetail = (ImageView) itemView.findViewById(R.id.img_thumbnail_film);
            fabButton = (FloatingActionButton) itemView.findViewById(R.id.fab);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

    }
}
