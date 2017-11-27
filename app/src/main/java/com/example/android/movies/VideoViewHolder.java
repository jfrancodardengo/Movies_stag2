package com.example.android.movies;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 2425115 on 27/11/2017.
 */

public class VideoViewHolder extends RecyclerView.ViewHolder {

    TextView name;

    public VideoViewHolder(View itemView) {
        super(itemView);
        name = (TextView)itemView.findViewById(R.id.name_video);
    }

    public TextView getName() {
        return name;
    }

    public void setName(TextView name) {
        this.name = name;
    }
}
