package com.example.android.movies;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by CASA on 10/12/2017.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.DetailViewHolder>{
    private Movie movie;
    private Context context;

    @Override
    public DetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_detalhe, parent, false);
        return new DetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DetailViewHolder holder, int position) {
        if(movie != null){
            Picasso.with(context).load(movie.getImageBack()).into(holder.thumbnailDetail);
        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailDetail;
        FloatingActionButton fabButton;


        public DetailViewHolder(View view) {
            super(view);

            thumbnailDetail = (ImageView) itemView.findViewById(R.id.img_thumbnail_film);
            fabButton = (FloatingActionButton) itemView.findViewById(R.id.fab);
        }
    }
}
