package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Guto on 15/10/2017.
 */

public class DetailActivity extends AppCompatActivity{
    Context context = DetailActivity.this;
    MainActivity mainActivity = new MainActivity();
    Movie movie = new Movie();
    TextView title, vote, release, synopsis;
    ImageView imageThumbnail;
    int movieId;


    private ArrayList<Reviews> reviews =new ArrayList<>();
    private ArrayList<Videos> videos =new ArrayList<>();

//    private String mVideos = mainActivity.URL_GENERIC + movie.getMovieId() + "/videos?api_key="+mainActivity.apiKey+"&language=pt-BR";
//    private String mVideos = mainActivity.URL_GENERIC +movieId+ "/videos?api_key="+mainActivity.apiKey+"&language=pt-BR";
    private String mReviews = mainActivity.URL_GENERIC + movie.getMovieId() + "/reviews?api_key="+mainActivity.apiKey+"&language=pt-BR";

    private static final int DATA_RESULT_LOADER_VIDEOS_ID = 1;
    private static final int DATA_RESULT_LOADER_REVIEWS_ID = 2;

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
        movie = i.getExtras().getParcelable("movie");

        movieId = movie.getMovieId();

        title.setText(movie.getOriginalTitle());
        vote.setText(String.valueOf(movie.getVoteAverage()));
        release.setText(movie.getRealeaseDate());
        synopsis.setText(movie.getSynopsis());

        Picasso.with(DetailActivity.this).load(movie.getImage()).into(imageThumbnail);

        Log.v("MOVIE ID ", String.valueOf(movie.getMovieId()));

        Bundle queryVideos = new Bundle();
        queryVideos.putString("url",mVideos);

        getSupportLoaderManager().initLoader(DATA_RESULT_LOADER_VIDEOS_ID,queryVideos,dataResultLoaderVideos);
//        getSupportLoaderManager().initLoader(DATA_RESULT_LOADER_VIDEOS_ID,null,dataResultLoaderVideos);

        Log.v("PASSOU ", "DAQUI");

    }

    private String mVideos = mainActivity.URL_GENERIC +movieId+ "/videos?api_key="+mainActivity.apiKey+"&language=pt-BR";

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
//                        Log.v("INTERNET: ", "CONNECTED");
                        Toast.makeText(context, "CONNECTED", Toast.LENGTH_SHORT).show();
                    }else{
                        Log.v("INTERNET: ", "DISCONNECTED");
                    }

                    forceLoad();
                }

                @Override
                public String loadInBackground() {
                    Log.v("URL VIDEO: ", mVideos);
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
                Boolean parse = new JSON(data,videos).parseVideos();

                if(parse){
                    Log.v("PARSE VIDEOS: ", String.valueOf(parse));
                    Log.v("URL VIDEO DEPOIS: ", data);
//                    recyclerView.setAdapter(new MovieAdapter(context, movies));
                }else {
                    Toast.makeText(context, "Unable To Parse,Check Your Log output", Toast.LENGTH_LONG).show();
                }
            }
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };

    private LoaderManager.LoaderCallbacks<String> dataResultLoaderReviews = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, Bundle args) {
            return null;
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {

        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };
}
