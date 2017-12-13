package com.example.android.movies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.movies.ItemClickListener;
import com.example.android.movies.R;

/**
 * Created by 2425115 on 27/11/2017.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView name, header;
    ItemClickListener itemClickListener;

    public VideoViewHolder(View itemView) {
        super(itemView);
        name = (TextView) itemView.findViewById(R.id.name_video);
        header = (TextView) itemView.findViewById(R.id.header_video);
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
