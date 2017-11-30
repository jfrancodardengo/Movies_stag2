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
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 2425115 on 27/11/2017.
 */

public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private Movie movie;
    private List<Videos> videos = new ArrayList<Videos>();
    private List<Reviews> reviews = new ArrayList<Reviews>();


    private final static int MOVIE=0, VIDEO = 1, REVIEWS = 2;

    public ComplexRecyclerViewAdapter() {
    }

    public ComplexRecyclerViewAdapter(Context context,Movie movie, List<Videos> videos, List<Reviews> reviews){
        this.context = context;
        this.movie = movie;
        this.videos = videos;
        this.reviews = reviews;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        RecyclerView.ViewHolder viewHolder;
//        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType){
            case VIDEO:
                return new VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.model_video,parent,false));
//                View view = inflater.inflate(R.layout.model_video,parent,false);
//                viewHolder = new VideoViewHolder(view);
//                break;
            case REVIEWS:
                return new ReviewViewHolder(LayoutInflater.from(context).inflate(R.layout.model_review,parent,false));
//                View view2 = inflater.inflate(R.layout.model_review,parent,false);
//                viewHolder = new ReviewViewHolder(view2);
//                break;
            case MOVIE:
                return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.model_overview,parent,false));
//                return new MovieViewHolder(LayoutInflater.from(context).inflate(R.layout.model_film,parent,false));
//                View view3 = inflater.inflate(R.layout.model_film,parent,false);
//                viewHolder = new MovieViewHolder(view3);
//                break;
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
//        return null;
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
            case MOVIE:
//              default:
                MovieViewHolder movieHolder = (MovieViewHolder) holder;
                configureMovieHolder(movieHolder,position);
                break;
        }

    }

    private void configureVideoHolder(VideoViewHolder vh1, int position) {
        final Videos video = videos.get(position - VIDEO);

            if(position == 1) {
                vh1.header.setVisibility(View.VISIBLE);
            }else{
                vh1.header.setVisibility(View.INVISIBLE);
            }
            if (video != null) {

                vh1.name.setText(video.getName());

                vh1.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
//                    Intent i = new Intent(context,DetailActivity.class);
//                    i.putExtra("video",video);
//                    context.startActivity(i);
                        Uri src = Uri.parse("https://www.youtube.com/watch?v=" + video.getKey());
                        Intent webIntent = new Intent(Intent.ACTION_VIEW, src);
                        context.startActivity(webIntent);
                    }
                });
            }


    }

    private void configureReviewHolder(ReviewViewHolder vh2, int position) {
        final Reviews review = reviews.get(position - REVIEWS);
        if (review != null) {
            vh2.author.setText(review.getAuthor());
            vh2.content.setText(review.getContent());
        }
    }

    private void configureMovieHolder(MovieViewHolder vh, int position) {
//        final Movie movie = movies.get(position);
        if (movie != null) {
            Picasso.with(context).load(movie.getImage()).into(vh.thumbnailDetail);

            vh.title.setText(movie.getOriginalTitle());
            vh.vote.setText(String.valueOf(movie.getVoteAverage()));
            vh.release.setText(movie.getRealeaseDate());
            vh.synopsis.setText(movie.getSynopsis());

            vh.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    Intent i = new Intent(context,DetailActivity.class);
                    i.putExtra("movie",movie);
                    context.startActivity(i);
                }
            });
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return MOVIE;
        }else if(position > videos.size()){
            return REVIEWS;
        }else{
            return VIDEO;
        }
    }

//    @Override
//    public int getItemViewType(int position) {
//        if(videos.get(position) instanceof Videos){
//            return VIDEO;
//        }else if(reviews.get(position) instanceof Reviews){
//            return REVIEWS;
//        }
//        return -1;
//    }


    @Override
    public int getItemCount() {
        return 1 + this.reviews.size() + this.videos.size();
    }

    public void addTrailers(List<Videos> videos){
        this.videos = videos;
    }

    public void addReviews(List<Reviews> reviews){
        this.reviews =  reviews;
    }

}
