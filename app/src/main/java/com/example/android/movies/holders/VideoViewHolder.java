package com.example.android.movies.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.ItemClickListener;
import com.example.android.movies.R;

/**
 * Created by 2425115 on 27/11/2017.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public final TextView name;
    public final ImageView imgThumbnailTrailer;

    private ItemClickListener itemClickListener;

    public VideoViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name_video);
        imgThumbnailTrailer = itemView.findViewById(R.id.img_thumbnail_trailer);

        itemView.setOnClickListener(this);
    }

    public void onClick(View v) {
        this.itemClickListener.onItemClick();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
