package com.example.android.movies.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.movies.R;

/**
 * Created by 2425115 on 27/11/2017.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    public final TextView author;
    public final TextView content;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        author = (TextView) itemView.findViewById(R.id.author);
        content = (TextView) itemView.findViewById(R.id.content);
    }

}
