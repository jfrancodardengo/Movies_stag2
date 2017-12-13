package com.example.android.movies;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.android.movies.adapters.FavoriteAdapter;
import com.example.android.movies.adapters.MovieAdapter;
import com.example.android.movies.data.Connector;
import com.example.android.movies.data.JSON;
import com.example.android.movies.data.Movie;
import com.example.android.movies.data.MoviesContract;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    Context context = MainActivity.this;
    RecyclerView recyclerView;

    public static final String URL_GENERIC = "https://api.themoviedb.org/3/movie/";

    public static final String apiKey = com.example.android.movies.BuildConfig.MOVIES_KEY;
    String jsonURLPopular = URL_GENERIC + "popular?api_key=" + apiKey + "&language=pt-BR";
    String jsonURLTopRated = URL_GENERIC + "top_rated?api_key=" + apiKey + "&language=pt-BR";

    String imageURL = "http://image.tmdb.org/t/p/";
    Boolean parse;
    ArrayList<Movie> movies = new ArrayList<>();

    FavoriteAdapter favoriteAdapter;
    MovieAdapter movieAdapter;

    private static final int LOADER_DETAIL = 1;
    private static final int LOADER_FAVORITE = 2;

    private static final String QUERY_URL = "";

    private String jsonUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_film);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        Bundle queryDetail = new Bundle();
        queryDetail.putString(QUERY_URL, jsonURLPopular);

        jsonUrl = queryDetail.getString(QUERY_URL);

        getSupportLoaderManager().initLoader(LOADER_DETAIL, queryDetail, dataResultLoaderDetail);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClick = item.getItemId();
        if (itemClick == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        } else if (itemClick == R.id.action_votes) {

            Bundle queryBundle = new Bundle();
            queryBundle.putString(QUERY_URL, jsonURLTopRated);

            jsonUrl = queryBundle.getString(QUERY_URL);

            getSupportLoaderManager().restartLoader(LOADER_DETAIL, queryBundle, dataResultLoaderDetail);

            return true;
        } else if (itemClick == R.id.action_favoritos) {
            getSupportLoaderManager().initLoader(LOADER_FAVORITE,null,dataResultLoaderFavorite);
            return true;

        }
        Bundle queryBundle = new Bundle();
        queryBundle.putString(QUERY_URL, jsonURLPopular);

        jsonUrl = queryBundle.getString(QUERY_URL);

        getSupportLoaderManager().restartLoader(LOADER_DETAIL, queryBundle, dataResultLoaderDetail);

        return super.onOptionsItemSelected(item);
    }


    private LoaderManager.LoaderCallbacks<String> dataResultLoaderDetail = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<String>(context) {

                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    if (args == null) {
                        return;
                    }

                    if (isConnected(context)) {
                        Log.v("INTERNET: ", "CONNECTED");
                    } else {
                        Log.v("INTERNET: ", "DISCONNECTED");
                    }

                    forceLoad();
                }

                @Override
                public String loadInBackground() {
                    return download(jsonUrl);
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {
            if (data.startsWith("Error")) {
                String error = data;

                Snackbar snackbar = Snackbar.make(findViewById(R.id.CoordinatorLayout),R.string.texto_offline,Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction(R.string.retry, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 0);
                    }
                });
                snackbar.show();

            } else {
                parse = new JSON(data, imageURL, movies).parse();

                if (parse) {
                    movieAdapter = new MovieAdapter(context, movies);
                    recyclerView.setAdapter(movieAdapter);
//                bindDataToAdapter();
                } else {
                    Toast.makeText(context, "Unable To Parse,Check Your Log output", Toast.LENGTH_LONG).show();
                }
            }
        }


        @Override
        public void onLoaderReset(Loader loader) {

        }
    };

    private LoaderManager.LoaderCallbacks<Cursor> dataResultLoaderFavorite = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            Uri CONTENT_URI = MoviesContract.MoviesEntry.CONTENT_URI;
            CursorLoader cursorLoader = new CursorLoader(context,CONTENT_URI,null,null,null,null);
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            data.moveToFirst();

            favoriteAdapter = new FavoriteAdapter(context,data);
            recyclerView.setAdapter(favoriteAdapter);

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };

    public String download(String url) {
        Object connection = Connector.connect(url);
        if (connection.toString().startsWith("Error")) {
            return connection.toString();
        }
        try {
            HttpURLConnection con = (HttpURLConnection) connection;
            if (con.getResponseCode() == con.HTTP_OK) {
                //GET INPUT FROM STREAM
                InputStream is = new BufferedInputStream(con.getInputStream());
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer jsonData = new StringBuffer();
                //READ
                while ((line = br.readLine()) != null) {
                    jsonData.append(line + "\n");
                }
                //CLOSE RESOURCES
                br.close();
                is.close();
                //RETURN JSON
                return jsonData.toString();
            } else {
                return "Error " + con.getResponseMessage();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Error " + e.getMessage();
        }
    }

    public static boolean isConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        if ((netInfo != null) && (netInfo.isConnectedOrConnecting()) && (netInfo.isAvailable())) {
            return true;
        }
        return false;
    }

}