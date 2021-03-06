package com.example.android.movies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.android.movies.R;
import com.example.android.movies.model.Reviews;
import com.example.android.movies.holders.ReviewViewHolder;

import java.util.List;

/**
 * Created by CASA on 10/12/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private final Context context;
    private final List<Reviews> reviews;

    public ReviewAdapter(Context context, List<Reviews> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @Override
    public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(context).inflate(R.layout.fragment_review, parent, false));
    }

    @Override
    public void onBindViewHolder(ReviewViewHolder vh2, int position) {
        final Reviews review = reviews.get(position);

        if (review != null) {
            vh2.author.setText(review.getAuthor());
            vh2.content.setText(review.getContent());
        }
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
