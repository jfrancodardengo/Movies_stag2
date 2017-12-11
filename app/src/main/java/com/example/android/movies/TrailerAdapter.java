package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CASA on 10/12/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    Context context;
    List<Videos> videos;


    public TrailerAdapter(Context context, List<Videos> videos) {
        this.context = context;
        this.videos = videos;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_trailer, parent, false));
    }

    @Override
    public void onBindViewHolder(VideoViewHolder vh1, int position) {
        final Videos video = videos.get(position);

        if (position == 0) {
            vh1.header.setVisibility(View.VISIBLE);
        } else {
            vh1.header.setVisibility(View.INVISIBLE);
        }

            if (video != null) {

                vh1.name.setText(video.getName());

                vh1.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Uri src = Uri.parse("https://www.youtube.com/watch?v=" + video.getKey());
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, src);
                        context.startActivity(webIntent);
                    }
                });
            }

    }

    @Override
    public int getItemCount() {
        return this.videos.size();
    }

    public void addTrailers(List<Videos> videos) {
        this.videos = videos;
        notifyDataSetChanged();
    }
}
