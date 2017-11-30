package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Guto on 15/10/2017.
 */


/*ULTIMA RESPOSTA PARA O FÓRUM
* I checked and is there necessary to put **String mVideos** and **String mReviews** inside `onCreate()` method after reading **Movie** from the Intent.
*
* */

public class DetailActivity extends AppCompatActivity{
    Context context = DetailActivity.this;
    MainActivity mainActivity = new MainActivity();
    Movie movie = new Movie();
    Videos video = new Videos();
    Reviews review = new Reviews();
    private List<Videos> videos = new ArrayList<Videos>();
    private List<Reviews> reviews = new ArrayList<Reviews>();


    TextView title, vote, release, synopsis;
    ImageView imageThumbnail;
    private String mVideos;
    private String mReviews;

    RecyclerView recyclerView;

    ComplexRecyclerViewAdapter adapter;

//    private ArrayList<Reviews> reviews =new ArrayList<>();
//    private ArrayList<Videos> videos =new ArrayList<>();

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

//        title = (TextView)findViewById(R.id.tv_title);
//        vote = (TextView)findViewById(R.id.tv_vote);
//        release = (TextView)findViewById(R.id.tv_release);
//        synopsis = (TextView)findViewById(R.id.tv_synopsis);
//        imageThumbnail = (ImageView)findViewById(R.id.img_thumbnail_film);

        //get intent movie
        Intent i = this.getIntent();
        movie = i.getExtras().getParcelable("movie");

        //get intent video
//        Intent v = this.getIntent();
//        video = v.getExtras().getParcelable("video");
//
//        Uri src = Uri.parse("https://www.youtube.com/watch?v="+video.getKey());
//        Intent webIntent = new Intent(Intent.ACTION_VIEW, src);
//        PackageManager packageManager = getPackageManager();
//        List<ResolveInfo> activities = packageManager.queryIntentActivities(webIntent, 0);
//        boolean isIntentSafe = activities.size() > 0;
//        // Start an activity if it's safe
//        if (isIntentSafe) {
//            startActivity(webIntent);
//        }
//
//        Log.v("TRAILER: ", video.getKey() + " - " + video.getName());
//        Log.v("URL TRAILER: ", src.toString());

        mVideos = mainActivity.URL_GENERIC +movie.getMovieId()+ "/videos?api_key="+mainActivity.apiKey+"&language=pt-BR";
        mReviews = mainActivity.URL_GENERIC + movie.getMovieId() + "/reviews?api_key="+mainActivity.apiKey+"&language=pt-BR";

//        title.setText(movie.getOriginalTitle());
//        vote.setText(String.valueOf(movie.getVoteAverage()));
//        release.setText(movie.getRealeaseDate());
//        synopsis.setText(movie.getSynopsis());
//
//        Picasso.with(DetailActivity.this).load(movie.getImage()).into(imageThumbnail);

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
//                        Log.v("INTERNET: ", "CONNECTED");
                        Toast.makeText(context, "CONNECTED", Toast.LENGTH_SHORT).show();
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
//                        Log.v("INTERNET: ", "CONNECTED");
                        Toast.makeText(context, "CONNECTED", Toast.LENGTH_SHORT).show();
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
