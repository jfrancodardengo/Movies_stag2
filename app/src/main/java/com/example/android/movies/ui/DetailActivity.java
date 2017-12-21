package com.example.android.movies.ui;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.movies.R;
import com.example.android.movies.model.Movie;
import com.example.android.movies.data.MoviesContract;
import com.example.android.movies.model.Videos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements TrailerFragment.Communication {
//    private Boolean isFavorite;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Movie movie;
    private ImageView moviePoster;
    private FloatingActionButton fabButton;
    private List<Videos> trailers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        moviePoster = (ImageView) findViewById(R.id.img_thumbnail_film);
        fabButton = (FloatingActionButton) findViewById(R.id.fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //get intent movie
        Intent i = this.getIntent();
        movie = i.getExtras().getParcelable("PARAM_MOVIE");
        Picasso.with(DetailActivity.this).load(movie.getImageBack()).into(moviePoster);


        if(isFavorite(movie.getMovieId())){
            fabButton.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this,R.drawable.ic_star_black_24dp));
        }else{
            fabButton.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this,R.drawable.ic_star_border_black_24dp));
        }

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new content values
                ContentValues contentValues = new ContentValues();
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_ID_MOVIE, movie.getMovieId());
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE, movie.getOriginalTitle());
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_VOTE_MOVIE, movie.getVoteAverage());
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_IMAGE_MOVIE, movie.getImage());
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_IMAGE_BACKGROUND_MOVIE, movie.getImageBack());
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_SYNOPSIS_MOVIE, movie.getSynopsis());
                contentValues.put(MoviesContract.MoviesEntry.COLUMN_RELEASE_MOVIE, movie.getRealeaseDate());

                Cursor favoritedMovie = getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,
                        null,
                        MoviesContract.MoviesEntry.COLUMN_ID_MOVIE + "=" + movie.getMovieId(),
                        null,
                        null);
                if (favoritedMovie.getCount() != 0) {
//                    isFavorite = true;

                    int idMovie = movie.getMovieId();
                    String stringId = Integer.toString(idMovie);
                    Uri uri = MoviesContract.MoviesEntry.CONTENT_URI;
                    uri = uri.buildUpon().appendPath(stringId).build();

                    DatabaseUtils.dumpCursor(favoritedMovie);

                    fabButton.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this,R.drawable.ic_star_border_black_24dp));

                    getContentResolver().delete(uri, MoviesContract.MoviesEntry.COLUMN_ID_MOVIE, new String[]{String.valueOf(idMovie)});
//                    Toast.makeText(DetailActivity.this, "Filme já foi favoritado!", Toast.LENGTH_LONG).show();
                } else {
//                    isFavorite = false;
                    Uri uri = DetailActivity.this.getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, contentValues);

                    if (uri != null) {
                        fabButton.setImageDrawable(ContextCompat.getDrawable(DetailActivity.this,R.drawable.ic_star_black_24dp));
//                        context.getContentResolver().update(uri,contentValues, MoviesContract.MoviesEntry.COLUMN_ID_MOVIE,new String[]{String.valueOf(idMovie)});
//                        Toast.makeText(DetailActivity.this, String.format("Filme %s favoritado!", movie.getOriginalTitle().toString()), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClick = item.getItemId();
        if (itemClick == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        } else if (itemClick == R.id.action_share) {
            item.setIntent(createShareVideoIntent());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OverviewFragment(), getString(R.string.overview));
        adapter.addFragment(new TrailerFragment(), getString(R.string.trailers));
        adapter.addFragment(new ReviewFragment(), getString(R.string.reviews));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onArticleSelected(List<Videos> position) {
        trailers = position;
    }

    public Intent createShareVideoIntent() {
        String urlVideo = String.format("https://www.youtube.com/watch?v=%s", trailers.get(0).getKey());
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle(trailers.get(0).getName())
                .setText(urlVideo)
                .getIntent();


        if (shareIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(shareIntent);
        }
        return shareIntent;
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    public boolean isFavorite(int movieId){
        Cursor favoritedMovie = getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,
                null,
                MoviesContract.MoviesEntry.COLUMN_ID_MOVIE + "=" + movieId,
                null,
                null);
        return favoritedMovie.getCount() !=0;
    }
}
