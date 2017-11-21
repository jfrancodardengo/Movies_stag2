package com.example.android.movies;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Guto on 15/10/2017.
 */

public class JSONParser  extends AsyncTask<Void,Void,Boolean>{
    Context context;
    String jsonData;
    String imageURL="http://image.tmdb.org/t/p/w342";
    RecyclerView recyclerView;
    ArrayList<Movie> movies =new ArrayList<>();

    public JSONParser(Context context, String jsonData, RecyclerView recyclerView) {
        this.context = context;
        this.jsonData = jsonData;
        this.recyclerView = recyclerView;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        JSON json = new JSON(jsonData,imageURL, movies);
        return json.parse();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);
        if(isParsed)
        {
            //BIND
            recyclerView.setAdapter(new MovieAdapter(context, movies));
        }else
        {
            Toast.makeText(context, "Unable To Parse,Check Your Log output", Toast.LENGTH_SHORT).show();
        }
    }

}

