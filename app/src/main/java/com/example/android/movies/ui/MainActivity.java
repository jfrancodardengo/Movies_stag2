package com.example.android.movies.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
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

import com.example.android.movies.R;
import com.example.android.movies.adapters.MovieAdapter;
import com.example.android.movies.data.Connector;
import com.example.android.movies.data.JSON;
import com.example.android.movies.model.Movie;
import com.example.android.movies.data.MoviesContract;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

import static com.example.android.movies.utils.Utils.isConnected;

public class MainActivity extends AppCompatActivity{

    public static final String URL_GENERIC = "https://api.themoviedb.org/3/movie/";
    public static final String API_KEY = com.example.android.movies.BuildConfig.MOVIES_KEY;
    public static final String JSON_URL_POPULAR = String.format("%spopular?api_key=%s&language=pt-BR", URL_GENERIC, API_KEY);
    public static final String JSON_URL_TOP_RATED = String.format("%stop_rated?api_key=%s&language=pt-BR", URL_GENERIC, API_KEY);
    public static final String IMAGE_URL = "http://image.tmdb.org/t/p/";
    private static final int LOADER_DETAIL = 1;
    private static final int LOADER_FAVORITE = 2;
    private static final String MOVIES_EXTRAS = "MOVIES_EXTRAS";
    private static final String ORDER_EXTRAS = "ORDER_EXTRAS";
    private static final String QUERY_URL = "";
    public static final String MY_PREFERENCES = "MY_PREFERENCES" ;
    public static final String POPULAR = "POPULAR";
    public static final String FAVORITOS = "FAVORITOS";
    public static final String VOTADOS = "VOTADOS";

//    public SharedPreferences sharedPreferences;
    private Boolean parse;
    private ArrayList<Movie> movies = new ArrayList<>();
    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private String jsonUrl;
    private String mSortBy;
    public SharedPreferences sharedPreferences;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_film);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
//        mSortBy = getString(R.string.acao_populares);
//        setAdapter(movies);

        Bundle queryDetail = new Bundle();
        queryDetail.putString(QUERY_URL, JSON_URL_POPULAR);
        jsonUrl = queryDetail.getString(QUERY_URL);
        if(isConnected(this)){
            getSupportLoaderManager().initLoader(LOADER_DETAIL, queryDetail, dataResultLoaderDetail);
        }else{
            errorConnection();
        }

        sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        movies = movieAdapter.getMovies();
        if (movies != null && !movies.isEmpty()) {
            outState.putParcelableArrayList(MOVIES_EXTRAS, movies);
        }
        outState.putString(ORDER_EXTRAS, mSortBy);

        Log.v("MOVIES EXTRAS: ", MOVIES_EXTRAS);

        super.onSaveInstanceState(outState);

    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSortBy = savedInstanceState.getString(ORDER_EXTRAS);
        movies = savedInstanceState.getParcelableArrayList(MOVIES_EXTRAS);
        setAdapter(movies);
        Log.v("RESTORE: ", "Chegou aqui.");

    }

    private void setAdapter(ArrayList<Movie> movies) {
        movieAdapter = new MovieAdapter(this, movies);
        recyclerView.setAdapter(movieAdapter);
        Log.v("SET ADAPTER: ", "Passou aqui.");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int itemClick = item.getItemId();
        if (itemClick == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        } else if (itemClick == R.id.action_votes) {
            mSortBy = getString(R.string.acao_votados);
            editor.putString(VOTADOS,mSortBy);

            Bundle queryBundle = new Bundle();
            queryBundle.putString(QUERY_URL, JSON_URL_TOP_RATED);
            jsonUrl = queryBundle.getString(QUERY_URL);
            if(isConnected(this)) {
                getSupportLoaderManager().restartLoader(LOADER_DETAIL, queryBundle, dataResultLoaderDetail);
            }else{
                errorConnection();
            }
        } else if (itemClick == R.id.action_favoritos) {
            mSortBy = getString(R.string.acao_favoritos);
            editor.putString(FAVORITOS,mSortBy);

            if(isConnected(this)){
                getSupportLoaderManager().initLoader(LOADER_FAVORITE, null, dataResultLoaderFavorite);
            }else{
                errorConnection();
            }
        } else if (itemClick == R.id.action_popular) {
            mSortBy = getString(R.string.acao_populares);
            editor.putString(POPULAR,mSortBy);

            Bundle queryBundle = new Bundle();
            queryBundle.putString(QUERY_URL, JSON_URL_POPULAR);
            jsonUrl = queryBundle.getString(QUERY_URL);
            if(isConnected(this)) {
                getSupportLoaderManager().restartLoader(LOADER_DETAIL, queryBundle, dataResultLoaderDetail);
            }else{
                errorConnection();
            }
        }
        editor.commit();
//        Log.v("PREFERENCIA SALVA: ", editor.toString());
        return super.onOptionsItemSelected(item);
    }

    private LoaderManager.LoaderCallbacks<String> dataResultLoaderDetail = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<String>(MainActivity.this) {

                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    if (args == null) {
                        return;
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
            parse = new JSON(data, IMAGE_URL, movies).parse();
            if (parse) {
                movieAdapter = new MovieAdapter(MainActivity.this, movies);
                recyclerView.setAdapter(movieAdapter);
            } else {
                Toast.makeText(MainActivity.this, "Unable To Parse,Check Your Log output", Toast.LENGTH_LONG).show();
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
            CursorLoader cursorLoader = new CursorLoader(MainActivity.this, CONTENT_URI, null, null, null, null);
            return cursorLoader;
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            cursor.moveToFirst();

            movies.clear();
            while (!cursor.isAfterLast()) {

                Movie movie = new Movie();
                movie.setMovieId(cursor.getInt(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_ID_MOVIE)));
                movie.setVoteAverage(cursor.getInt(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_ID_MOVIE)));
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_ID_MOVIE)));
                movie.setImage(cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_IMAGE_MOVIE)));
                movie.setImageBack(cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_IMAGE_BACKGROUND_MOVIE)));
                movie.setSynopsis(cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_SYNOPSIS_MOVIE)));
                movie.setRealeaseDate(cursor.getString(cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_RELEASE_MOVIE)));
                movies.add(movie);
                cursor.moveToNext();
            }
            movieAdapter = new MovieAdapter(MainActivity.this, movies);
            recyclerView.setAdapter(movieAdapter);

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

    public void errorConnection() {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.CoordinatorLayout), R.string.texto_offline, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction(R.string.retry, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 0);
            }
        });
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences.registerOnSharedPreferenceChangeListener(mListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(mListener);
    }

    SharedPreferences.OnSharedPreferenceChangeListener mListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            Preference pref = (Preference) sharedPreferences;
            if (key.equals(POPULAR)) {
                pref.setSummary(sharedPreferences.getString(key, mSortBy));
                Log.v("PREFERENCIA POPULAR: ", pref.getKey());
            } else if (key.equals(VOTADOS)) {
                pref.setSummary(sharedPreferences.getString(key, mSortBy));
                Log.v("PREFERENCIA votados: ", pref.getKey());
            } else if (key.equals(FAVORITOS)) {
                pref.setSummary(sharedPreferences.getString(key, mSortBy));
                Log.v("PREFERENCIA FAVORITOS: ", pref.getKey());
            }
        }


    };

}