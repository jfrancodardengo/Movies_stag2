package com.example.android.movies.data;

import com.example.android.movies.model.Movie;
import com.example.android.movies.model.Reviews;
import com.example.android.movies.model.Videos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Guto on 19/10/2017.
 */

public class JSON {
    private String jsonData;
    private String imageURL;
    private ArrayList<Movie> movies = new ArrayList<>();
    private static ArrayList<Reviews> reviews = new ArrayList<>();
    private static ArrayList<Videos> videos = new ArrayList<>();

    public static String BACK_SIZE_0 = "w300";
    public static String BACK_SIZE_1 = "w780";
    public static String BACK_SIZE_2 = "w1280";
    public static String BACK_SIZE_3 = "original";

    public static String POSTER_SIZE_0 = "w92";
    public static String POSTER_SIZE_1 = "w154";
    public static String POSTER_SIZE_2 = "w185";
    public static String POSTER_SIZE_3 = "w342";
    public static String POSTER_SIZE_4 = "w500";
    public static String POSTER_SIZE_5 = "w780";
    public static String POSTER_SIZE_6 = "original";

    public JSON(String jsonData, String imageURL, ArrayList<Movie> movies) {
        this.jsonData = jsonData;
        this.imageURL = imageURL;
        this.movies = movies;
    }

    public JSON(String jsonData, ArrayList<Videos> videos) {
        this.jsonData = jsonData;
        this.videos = videos;
    }


    public Boolean parse() {
        try {
            JSONObject reader = new JSONObject(jsonData);
            JSONArray jsonArray = reader.getJSONArray("results");
            JSONObject jsonObject;
            movies.clear();
            Movie movie;

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                int idMovie = jsonObject.getInt("id");
                Double voteAverage = jsonObject.getDouble("vote_average");
                String title = jsonObject.getString("title");
                String image = jsonObject.getString("poster_path");
                String imageBack = jsonObject.getString("backdrop_path");
                String synopsis = jsonObject.getString("overview");
                String release = jsonObject.getString("release_date");

                String urlImagem = imageURL + POSTER_SIZE_3 + image;
                String urlImagemBack = imageURL + BACK_SIZE_2 + imageBack;

                String date = convertDate(release);

                movie = new Movie();
                movie.setMovieId(idMovie);
                movie.setVoteAverage(voteAverage);
                movie.setOriginalTitle(title);
                movie.setImage(urlImagem);
                movie.setImageBack(urlImagemBack);
                movie.setSynopsis(synopsis);
                movie.setRealeaseDate(date);
                movies.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static ArrayList<Reviews> parseReviews(String jsonData) {
        try {
            JSONObject reader = new JSONObject(jsonData);
            JSONArray jsonArray = reader.getJSONArray("results");
            JSONObject jsonObject;
            reviews.clear();
            Reviews review;

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String author = jsonObject.getString("author");
                String content = jsonObject.getString("content");

                review = new Reviews();
                review.setAuthor(author);
                review.setContent(content);
                reviews.add(review);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviews;
    }

    public static ArrayList<Videos> parseVideos(String jsonData) {
        try {
            JSONObject reader = new JSONObject(jsonData);
            JSONArray jsonArray = reader.getJSONArray("results");
            JSONObject jsonObject;
            videos.clear();
            Videos video;

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                String key = jsonObject.getString("key");
                String name = jsonObject.getString("name");

                video = new Videos();
                video.setKey(key);
                video.setName(name);
                videos.add(video);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videos;
    }

    public String convertDate(String dateOriginal) throws ParseException {

        //2017-11-15 - dateOriginal
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date data = formato.parse(dateOriginal);
        formato.applyPattern("yyyy");
        String destiny = formato.format(data);

        return destiny;
    }

}
