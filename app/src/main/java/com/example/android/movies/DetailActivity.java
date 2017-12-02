package com.example.android.movies;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movies.data.MoviesContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guto on 15/10/2017.
 */

public class DetailActivity extends AppCompatActivity{
    Context context = DetailActivity.this;
    MainActivity mainActivity = new MainActivity();
    Movie movie = new Movie();
    private List<Videos> videos = new ArrayList<Videos>();
    private List<Reviews> reviews = new ArrayList<Reviews>();

    private String mVideos;
    private String mReviews;

    RecyclerView recyclerView;

    ComplexRecyclerViewAdapter adapter;

    private static final int DATA_RESULT_LOADER_VIDEOS_ID = 1;
    private static final int DATA_RESULT_LOADER_REVIEWS_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //get intent movie
        Intent i = this.getIntent();
        movie = i.getExtras().getParcelable("movie");


//        Log.v("TRAILER: ", video.getKey() + " - " + video.getName());
//        Log.v("URL TRAILER: ", src.toString());

        mVideos = mainActivity.URL_GENERIC +movie.getMovieId()+ "/videos?api_key="+mainActivity.apiKey+"&language=pt-BR";
        mReviews = mainActivity.URL_GENERIC + movie.getMovieId() + "/reviews?api_key="+mainActivity.apiKey+"&language=pt-BR";


        Log.v("MOVIE: ", movie.getOriginalTitle());
        Log.v("MOVIE ID: ", String.valueOf(movie.getMovieId()));

        adapter = new ComplexRecyclerViewAdapter(context,movie,videos,reviews);
        recyclerView.setAdapter(adapter);

        Bundle queryVideos = new Bundle();
        queryVideos.putString("url",mVideos);
        getSupportLoaderManager().initLoader(DATA_RESULT_LOADER_VIDEOS_ID,queryVideos,dataResultLoaderVideos);

        Bundle queryReviews = new Bundle();
        queryVideos.putString("url",mReviews);
        getSupportLoaderManager().initLoader(DATA_RESULT_LOADER_REVIEWS_ID,queryReviews,dataResultLoaderReviews);


        /*
         Set the Floating Action Button (FAB) to its corresponding View.
         Attach an OnClickListener to it, so that when it's clicked, a new intent will be created
         to launch the AddTaskActivity.
         */
        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //new content values
                ContentValues contentValues = new ContentValues();
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_ID_MOVIE, movie.getMovieId());
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE, movie.getOriginalTitle());
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_MOVIE, movie.getVoteAverage());
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_IMAGE_MOVIE, movie.getImage());
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_SYNOPSIS_MOVIE, movie.getSynopsis());
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_RELEASE_MOVIE, movie.getRealeaseDate());

                Uri uri = getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI,contentValues);

                if(uri != null){
                    Toast.makeText(getBaseContext(),uri.toString(),Toast.LENGTH_LONG).show();
                }

                finish();
            }
        });

    }

    private LoaderManager.LoaderCallbacks<String> dataResultLoaderVideos = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<String>(context) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    if(args == null) {
                        Log.v("ARGS DETAIL: ", String.valueOf(args));
                        return;
                    }

                    if(mainActivity.isConnected(context)){
                        Log.v("INTERNET: ", "CONNECTED");
                    }else{
                        Log.v("INTERNET: ", "DISCONNECTED");
                    }

                    forceLoad();
                }

                @Override
                public String loadInBackground() {
                    return mainActivity.download(mVideos);
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {
            if (data.startsWith("Error")) {
                String error = data;
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            } else {
                videos = JSON.parseVideos(data);
                adapter.addTrailers(videos);
                adapter.notifyDataSetChanged();

                Log.v("VIDEOS: ", String.valueOf(videos.toArray().length));

            }
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };

    private LoaderManager.LoaderCallbacks<String> dataResultLoaderReviews = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<String>(context) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    if(args == null) {
                        Log.v("ARGS DETAIL: ", String.valueOf(args));
                        return;
                    }

                    if(mainActivity.isConnected(context)){
                        Log.v("INTERNET: ", "CONNECTED");
                    }else{
                        Log.v("INTERNET: ", "DISCONNECTED");
                    }

                    forceLoad();
                }

                @Override
                public String loadInBackground() {
                    return mainActivity.download(mReviews);
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {
            if (data.startsWith("Error")) {
                String error = data;
                Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
            } else {
//                JSON.parseReviews(data);
                reviews = JSON.parseReviews(data);
                adapter.addReviews(reviews);
                adapter.notifyDataSetChanged();

                Log.v("REVIEWS: ", String.valueOf(reviews.toArray().length));
            }
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };

}
