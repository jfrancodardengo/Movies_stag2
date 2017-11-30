package com.example.android.movies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Guto on 15/10/2017.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    ImageView thumbnailFilm;
    ImageView thumbnailDetail;
    TextView title, vote, release, synopsis;


    ItemClickListener itemClickListener;

    public MovieViewHolder(View itemView){
        super(itemView);

        thumbnailFilm = (ImageView)itemView.findViewById(R.id.thumbnail_film);

        title = (TextView)itemView.findViewById(R.id.tv_title);
        vote = (TextView)itemView.findViewById(R.id.tv_vote);
        release = (TextView)itemView.findViewById(R.id.tv_release);
        synopsis = (TextView)itemView.findViewById(R.id.tv_synopsis);
        thumbnailDetail = (ImageView) itemView.findViewById(R.id.img_thumbnail_film);

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
