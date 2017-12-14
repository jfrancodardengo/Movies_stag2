package com.example.android.movies.adapters;

import android.support.v7.widget.CardView;
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
    TextView name;
    ImageView imgThumbnailTrailer;

    ItemClickListener itemClickListener;

    public VideoViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name_video);
        imgThumbnailTrailer = (ImageView) itemView.findViewById(R.id.img_thumbnail_trailer);

        itemView.setOnClickListener(this);
    }

    public void onClick(View v) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }
}
