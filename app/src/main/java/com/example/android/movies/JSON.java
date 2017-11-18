package com.example.android.movies;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Guto on 19/10/2017.
 */

public class JSON {
    private String jsonData;
    private String imageURL;
    private ArrayList<Movie> movies =new ArrayList<>();

    public JSON(String jsonData, String imageURL, ArrayList<Movie> movies) {
        this.jsonData = jsonData;
        this.imageURL = imageURL;
        this.movies = movies;
    }

    public Boolean parse(){
        try
        {
            JSONObject reader = new JSONObject(jsonData);
            JSONArray jsonArray = reader.getJSONArray("results");
            JSONObject jsonObject;
            movies.clear();
            Movie movie;

            for (int i=0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                Double voteAverage = jsonObject.getDouble("vote_average");
                String title = jsonObject.getString("title");
                String image = jsonObject.getString("poster_path");
                String synopsis = jsonObject.getString("overview");
                String release = jsonObject.getString("release_date");
                String urlImagem = imageURL+image;

                movie = new Movie();
                movie.setVoteAverage(voteAverage);
                movie.setOriginalTitle(title);
                movie.setImage(urlImagem);
                movie.setSynopsis(synopsis);
                movie.setRealeaseDate(release);
                movies.add(movie);
            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }


}
