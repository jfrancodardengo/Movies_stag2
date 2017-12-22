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
import java.util.Locale;

/**
 * Created by Guto on 19/10/2017.
 */

public class JSON {
    private static final ArrayList<Reviews> reviews = new ArrayList<>();
    private static final ArrayList<Videos> videos = new ArrayList<>();
    private static final String BACK_SIZE_2 = "w1280";
    private static final String POSTER_SIZE_3 = "w342";

    private final String jsonData;
    private final String imageURL;
    private ArrayList<Movie> movies = new ArrayList<>();

    public JSON(String jsonData, ArrayList<Movie> movies) {
        this.jsonData = jsonData;
        this.imageURL = com.example.android.movies.ui.MainActivity.IMAGE_URL;
        this.movies = movies;
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

                String urlImagem = String.format("%s%s%s", imageURL, POSTER_SIZE_3, image);
                String urlImagemBack = String.format("%s%s%s", imageURL, BACK_SIZE_2, imageBack);

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

    private String convertDate(String dateOriginal) throws ParseException {
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date data = formato.parse(dateOriginal);
        formato.applyPattern("yyyy");
        return formato.format(data);
    }

}
