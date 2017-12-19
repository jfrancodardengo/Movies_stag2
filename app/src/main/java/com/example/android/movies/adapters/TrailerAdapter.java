package com.example.android.movies.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.movies.ItemClickListener;
import com.example.android.movies.R;
import com.example.android.movies.model.Videos;
import com.example.android.movies.holders.VideoViewHolder;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by CASA on 10/12/2017.
 */

public class TrailerAdapter extends RecyclerView.Adapter<VideoViewHolder> {
    private Context context;
    private List<Videos> videos;


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

            if (video != null) {

                String urlImageVideo = String.format("http://img.youtube.com/vi/%s/0.jpg", video.getKey());

                vh1.name.setText(video.getName());
                Picasso.with(context).load(urlImageVideo).into(vh1.imgThumbnailTrailer);

                vh1.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        Uri src = Uri.parse(String.format("https://www.youtube.com/watch?v=%s", video.getKey()));
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

}
