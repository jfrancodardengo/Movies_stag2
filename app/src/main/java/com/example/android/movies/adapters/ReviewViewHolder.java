package com.example.android.movies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.android.movies.R;

/**
 * Created by 2425115 on 27/11/2017.
 */

public class ReviewViewHolder extends RecyclerView.ViewHolder {

    TextView author, content;

    public ReviewViewHolder(View itemView) {
        super(itemView);
        author = (TextView) itemView.findViewById(R.id.author);
        content = (TextView) itemView.findViewById(R.id.content);
    }

    public TextView getAuthor() {
        return author;
    }

    public void setAuthor(TextView author) {
        this.author = author;
    }

    public TextView getContent() {
        return content;
    }

    public void setContent(TextView content) {
        this.content = content;
    }
}
