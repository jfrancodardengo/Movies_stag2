package com.example.android.movies.UI;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.android.movies.R;
import com.example.android.movies.data.Movie;
import com.example.android.movies.data.MoviesContract;
import com.example.android.movies.data.Videos;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class DetalheActivity extends AppCompatActivity implements TrailerFragment.Communication{
    Context context = DetalheActivity.this;
    TrailerFragment trailerFragment = new TrailerFragment();

    private TabLayout tabLayout;
    private ViewPager viewPager;

    Movie movie;

    ImageView moviePoster;
    FloatingActionButton fabButton;

    List<Videos> trailers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        moviePoster = (ImageView)findViewById(R.id.img_thumbnail_film);
        fabButton = (FloatingActionButton) findViewById(R.id.fab);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        //get intent movie
        Intent i = this.getIntent();
        movie = i.getExtras().getParcelable("movie");

        Picasso.with(context).load(movie.getImageBack()).into(moviePoster);

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

                Uri uri = context.getContentResolver().insert(MoviesContract.MoviesEntry.CONTENT_URI, contentValues);

                if (uri != null) {
//                        context.getContentResolver().update(uri,contentValues, MoviesContract.MoviesEntry.COLUMN_ID_MOVIE,new String[]{String.valueOf(idMovie)});
                    Toast.makeText(context,"Filme " + movie.getOriginalTitle().toString() +" favoritado!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClick = item.getItemId();
        if (itemClick == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }else if (itemClick == R.id.action_share){
            item.setIntent(createShareVideoIntent());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new OverviewFragment(),getString(R.string.overview));
        adapter.addFragment(new TrailerFragment(), getString(R.string.trailers));
        adapter.addFragment(new ReviewFragment(), getString(R.string.reviews));
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onArticleSelected(List<Videos> position) {
        trailers = position;
    }

    public Intent createShareVideoIntent() {
        String urlVideo = "https://www.youtube.com/watch?v=" + trailers.get(0).getKey();
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setChooserTitle(trailers.get(0).getName())
                .setText(urlVideo)
                .getIntent();


        if (shareIntent.resolveActivity(getPackageManager()) != null){
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
}
