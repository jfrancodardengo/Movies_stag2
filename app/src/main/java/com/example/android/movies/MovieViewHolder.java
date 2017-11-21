package com.example.android.movies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Guto on 15/10/2017.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView thumbnailFilm;
    ItemClickListener itemClickListener;

    public MovieViewHolder(View itemView){
        super(itemView);

        thumbnailFilm = (ImageView)itemView.findViewById(R.id.thumbnail_film);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }
}
