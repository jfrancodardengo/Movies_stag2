package com.example.android.movies.holders;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.ItemClickListener;
import com.example.android.movies.R;

/**
 * Created by Guto on 15/10/2017.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final ImageView thumbnailFilm;
    public final ImageView thumbnailDetail;
    public final ImageView thumbnailDetailCard;
    public final TextView title;
    public final TextView vote;
    public final TextView release;
    public final TextView synopsis;
    public final FloatingActionButton fabButton;
    private ItemClickListener itemClickListener;

    public MovieViewHolder(View itemView) {
        super(itemView);
        thumbnailFilm = (ImageView) itemView.findViewById(R.id.thumbnail_film);
        title = (TextView) itemView.findViewById(R.id.tv_title);
        vote = (TextView) itemView.findViewById(R.id.tv_vote);
        release = (TextView) itemView.findViewById(R.id.tv_release);
        synopsis = (TextView) itemView.findViewById(R.id.tv_synopsis);
        thumbnailDetail = (ImageView) itemView.findViewById(R.id.img_thumbnail_film);
        thumbnailDetailCard = (ImageView) itemView.findViewById(R.id.img_thumbnail_cardview);
        fabButton = (FloatingActionButton) itemView.findViewById(R.id.fab);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


}
