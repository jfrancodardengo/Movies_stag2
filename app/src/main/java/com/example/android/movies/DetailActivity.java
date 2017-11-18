package com.example.android.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Guto on 15/10/2017.
 */

public class DetailActivity extends AppCompatActivity{
    TextView title, vote, release, synopsis;
    ImageView imageThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        title = (TextView)findViewById(R.id.tv_title);
        vote = (TextView)findViewById(R.id.tv_vote);
        release = (TextView)findViewById(R.id.tv_release);
        synopsis = (TextView)findViewById(R.id.tv_synopsis);
        imageThumbnail = (ImageView)findViewById(R.id.img_thumbnail_film);

        //obter intent
        Intent i = this.getIntent();

        //receber dados
        double mVoteAverage = i.getExtras().getDouble("VOTE_KEY");
        String mOriginalTitle = i.getExtras().getString("TITLE_KEY");
        String mImage = i.getExtras().getString("IMAGE_KEY");
        String mSynopsis = i.getExtras().getString("SYNOPSIS_KEY");
        String mRealeaseDate = i.getExtras().getString("RELEASE_KEY");

//        String dtLancamento = String.valueOf(mRealeaseDate);

        //criar dados
        title.setText(mOriginalTitle);
        vote.setText(String.valueOf(mVoteAverage));
        release.setText(mRealeaseDate);
        synopsis.setText(mSynopsis);

        Picasso.with(DetailActivity.this).load(mImage).into(imageThumbnail);

    }
}
