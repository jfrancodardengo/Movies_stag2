package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.MediaStore;
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
    private List<Movie> movies;
    private Movie movie;
    private List<Videos> videos;
    private List<Reviews> reviews;


    private final int VIDEO = 0, REVIEWS = 1;

    public ComplexRecyclerViewAdapter() {
    }

    public ComplexRecyclerViewAdapter(Movie movie, List<Videos> videos, List<Reviews> reviews){
        this.movie = movie;
        this.videos = videos;
        this.reviews = reviews;
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
        final Videos video = videos.get(position);
        if (video != null) {
            vh1.name.setText(video.getName());

            vh1.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Uri src = Uri.parse("https://www.youtube.com/watch?v="+video.getKey());
                    Intent webIntent = new Intent(Intent.ACTION_VIEW, src);
                    context.startActivity(webIntent);
                }
            });
        }
    }

    private void configureReviewHolder(ReviewViewHolder vh2, int position) {
        final Reviews review = reviews.get(position);
        if (review != null) {
            vh2.author.setText(review.getAuthor());
            vh2.content.setText(review.getContent());
        }
    }

    private void configureMovieHolder(MovieViewHolder vh, int position) {
        final Movie movie = movies.get(position);
        if (movie != null) {
            Picasso.with(context).load(movie.getImage()).into(vh.thumbnailFilm);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if(videos.get(position) instanceof Videos){
            return VIDEO;
        }else if(reviews.get(position) instanceof Reviews){
            return REVIEWS;
        }
        return -1;
    }


    @Override
    public int getItemCount() {
        return this.movies.size() + this.reviews.size() + this.videos.size();
    }

    public void addTrailers(List<Videos> videos){
        this.videos = videos;
    }

    public void addReviews(List<Reviews> reviews){
        this.reviews =  reviews;
    }

}
