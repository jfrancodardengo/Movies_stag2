package com.example.android.movies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    Context context = MainActivity.this;
    RecyclerView recyclerView;

    public static final String URL_GENERIC = "https://api.themoviedb.org/3/movie/";

    public static final String apiKey = com.example.android.movies.BuildConfig.MOVIES_KEY;
    String jsonURLPopular = URL_GENERIC + "popular?api_key=" + apiKey + "&language=pt-BR";
    String jsonURLTopRated = URL_GENERIC + "top_rated?api_key=" + apiKey + "&language=pt-BR";

    String imageURL = "http://image.tmdb.org/t/p/w342";
    Boolean parse;
    ArrayList<Movie> movies = new ArrayList<>();
    ArrayList<Videos> videos = new ArrayList<>();
    ArrayList<Reviews> reviews = new ArrayList<>();

    Movie movie;

    private static final int LOADER = 1;

    private static final String QUERY_URL = "";

    private String jsonUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_film);
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
//        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        Bundle queryBundle = new Bundle();
        queryBundle.putString(QUERY_URL, jsonURLPopular);

        jsonUrl = queryBundle.getString(QUERY_URL);

        getSupportLoaderManager().initLoader(LOADER, queryBundle, this);
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

            getSupportLoaderManager().restartLoader(LOADER, queryBundle, this);

            return true;
        }
        Bundle queryBundle = new Bundle();
        queryBundle.putString(QUERY_URL, jsonURLPopular);

        jsonUrl = queryBundle.getString(QUERY_URL);

        getSupportLoaderManager().restartLoader(LOADER, queryBundle, this);

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

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
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show();
        } else {
            parse = new JSON(data, imageURL, movies).parse();

            if (parse) {
                recyclerView.setAdapter(new MovieAdapter(context, movies));
//                bindDataToAdapter();
            } else {
                Toast.makeText(context, "Unable To Parse,Check Your Log output", Toast.LENGTH_LONG).show();
            }
        }
    }

//    private void bindDataToAdapter() {
//        // Bind adapter to recycler view object
//        recyclerView.setAdapter(new ComplexRecyclerViewAdapter(movies,videos,reviews));
//    }

    @Override
    public void onLoaderReset(Loader loader) {

    }

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
