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
import android.support.v4.app.NavUtils;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class DetailActivity extends AppCompatActivity {
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
        recyclerView = (RecyclerView) findViewById(R.id.recycler_detail);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        //get intent movie
        Intent i = this.getIntent();
        movie = i.getExtras().getParcelable("movie");


//        Log.v("TRAILER: ", video.getKey() + " - " + video.getName());
//        Log.v("URL TRAILER: ", src.toString());

        mVideos = mainActivity.URL_GENERIC + movie.getMovieId() + "/videos?api_key=" + mainActivity.apiKey + "&language=pt-BR";
        mReviews = mainActivity.URL_GENERIC + movie.getMovieId() + "/reviews?api_key=" + mainActivity.apiKey + "&language=pt-BR";


        Log.v("MOVIE: ", movie.getOriginalTitle());
        Log.v("MOVIE ID: ", String.valueOf(movie.getMovieId()));

        adapter = new ComplexRecyclerViewAdapter(context, movie, videos, reviews);
        recyclerView.setAdapter(adapter);

        Bundle queryVideos = new Bundle();
        queryVideos.putString("url", mVideos);
        getSupportLoaderManager().initLoader(DATA_RESULT_LOADER_VIDEOS_ID, queryVideos, dataResultLoaderVideos);

        Bundle queryReviews = new Bundle();
        queryVideos.putString("url", mReviews);
        getSupportLoaderManager().initLoader(DATA_RESULT_LOADER_REVIEWS_ID, queryReviews, dataResultLoaderReviews);

    }

    private LoaderManager.LoaderCallbacks<String> dataResultLoaderVideos = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<String>(context) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    if (args == null) {
                        Log.v("ARGS DETAIL: ", String.valueOf(args));
                        return;
                    }

                    if (mainActivity.isConnected(context)) {
                        Log.v("INTERNET: ", "CONNECTED");
                    } else {
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
                    if (args == null) {
                        Log.v("ARGS DETAIL: ", String.valueOf(args));
                        return;
                    }

                    if (mainActivity.isConnected(context)) {
                        Log.v("INTERNET: ", "CONNECTED");
                    } else {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClick = item.getItemId();
        if (itemClick == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        } else if (itemClick == R.id.action_share){
            item.setIntent(createShareVideoIntent());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private Intent createShareVideoIntent() {
        String urlVideo = "https://www.youtube.com/watch?v=" + videos.get(0).getKey();
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle(videos.get(0).getName())
                .setText(urlVideo)
                .getIntent();

        if (shareIntent.resolveActivity(getPackageManager()) != null){
            startActivity(shareIntent);
        }
        return shareIntent;
    }
}
