package com.example.android.movies.holders;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.ItemClickListener;
import com.example.android.movies.R;

/**
 * Created by Guto on 15/10/2017.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final ImageView thumbnailFilm;
    public final ImageView thumbnailDetailCard;
    public final TextView title;
    public final TextView vote;
    public final TextView release;
    public final TextView synopsis;
    private ItemClickListener itemClickListener;

    public MovieViewHolder(View itemView) {
        super(itemView);
        thumbnailFilm = itemView.findViewById(R.id.thumbnail_film);
        title = itemView.findViewById(R.id.tv_title);
        vote = itemView.findViewById(R.id.tv_vote);
        release = itemView.findViewById(R.id.tv_release);
        synopsis = itemView.findViewById(R.id.tv_synopsis);
        ImageView thumbnailDetail = itemView.findViewById(R.id.img_thumbnail_film);
        thumbnailDetailCard = itemView.findViewById(R.id.img_thumbnail_cardview);
        FloatingActionButton fabButton = itemView.findViewById(R.id.fab);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


}
