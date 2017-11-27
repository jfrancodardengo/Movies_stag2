package com.example.android.movies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2425115 on 27/11/2017.
 */

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Object> items;

    private final int VIDEO = 0, REVIEWS = 1;

    public ComplexRecyclerViewAdapter(List<Object> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case VIDEO:
                View view = inflater.inflate(R.layout.model_video,parent,false);
                viewHolder = new VideoViewHolder(view);
                break;
            case REVIEWS:
                View view2 = inflater.inflate(R.layout.model_review,parent,false);
                viewHolder = new ReviewViewHolder(view2);
                break;
            default:
                View view3 = inflater.inflate(R.layout.model_film,parent,false);
                viewHolder = new MovieViewHolder(view3);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (holder.getItemViewType()){
            case VIDEO:
                VideoViewHolder videoHolder = (VideoViewHolder) holder;
                configureVideoHolder(videoHolder,position);
                break;
            case REVIEWS:
                ReviewViewHolder reviewHolder = (ReviewViewHolder) holder;
                configureReviewHolder(reviewHolder,position);
                break;
            default:
                MovieViewHolder movieHolder = (MovieViewHolder) holder;
                configureMovieHolder(movieHolder,position);
                break;
        }

    }

    private void configureVideoHolder(VideoViewHolder vh1, int position) {
        final Videos video = (Videos) items.get(position);
        if (video != null) {
            vh1.name.setText(video.getName());
        }
    }

    private void configureReviewHolder(ReviewViewHolder vh2, int position) {
        final Reviews review = (Reviews) items.get(position);
        if (review != null) {
            vh2.author.setText(review.getAuthor());
            vh2.content.setText(review.getContent());
        }
    }

    private void configureMovieHolder(MovieViewHolder vh, int position) {
        final Movie movie = (Movie) items.get(position);
        if (movie != null) {
            Picasso.with(context).load(movie.getImage()).into(vh.thumbnailFilm);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(items.get(position) instanceof Videos){
            return VIDEO;
        }else if(items.get(position) instanceof Reviews){
            return REVIEWS;
        }
        return -1;
    }


    @Override
    public int getItemCount() {
        return this.items.size();
    }
}
