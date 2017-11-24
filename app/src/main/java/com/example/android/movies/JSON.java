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
    private ArrayList<Reviews> reviews =new ArrayList<>();
    private ArrayList<Videos> videos =new ArrayList<>();
//    private int idMovie;

    public JSON(String jsonData, String imageURL, ArrayList<Movie> movies) {
        this.jsonData = jsonData;
        this.imageURL = imageURL;
        this.movies = movies;
    }

    public JSON(String jsonData, ArrayList<Videos> videos) {
        this.jsonData = jsonData;
        this.videos = videos;
    }

//    public JSON(String jsonData, ArrayList<Reviews> reviews) {
//        this.jsonData = jsonData;
//        this.reviews = reviews;
//    }

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
                int idMovie = jsonObject.getInt("id");
                Double voteAverage = jsonObject.getDouble("vote_average");
                String title = jsonObject.getString("title");
                String image = jsonObject.getString("poster_path");
                String synopsis = jsonObject.getString("overview");
                String release = jsonObject.getString("release_date");
                String urlImagem = imageURL+image;

                movie = new Movie();
                movie.setMovieId(idMovie);
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

    public Boolean parseReviews(){
        try
        {
            JSONObject reader = new JSONObject(jsonData);
            JSONArray jsonArray = reader.getJSONArray("results");
            JSONObject jsonObject;
            reviews.clear();
            Reviews review;

            for (int i=0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                String author = jsonObject.getString("author");
                String content = jsonObject.getString("content");

                review = new Reviews();
                review.setAuthor(author);
                review.setContent(content);
                reviews.add(review);

            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Boolean parseVideos(){
        try
        {
            JSONObject reader = new JSONObject(jsonData);
            JSONArray jsonArray = reader.getJSONArray("results");
            JSONObject jsonObject;
            videos.clear();
            Videos video;

            for (int i=0; i < jsonArray.length(); i++){
                jsonObject = jsonArray.getJSONObject(i);
                String key = jsonObject.getString("key");
                String name = jsonObject.getString("name");

                video = new Videos();
                video.setKey(key);
                video.setName(name);
                videos.add(video);

            }
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }


}
