package com.example.android.movies.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.movies.R;
import com.example.android.movies.adapters.ReviewAdapter;
import com.example.android.movies.data.JSON;
import com.example.android.movies.model.Movie;
import com.example.android.movies.model.Reviews;

import java.util.ArrayList;
import java.util.List;

import static com.example.android.movies.utils.Utils.isConnected;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {
    private static final int DATA_RESULT_LOADER_REVIEWS_ID = 2;

    MainActivity mainActivity = new MainActivity();
    private Movie movie;
    private RecyclerView mRecyclerView;
    private ReviewAdapter adapter;
    private List<Reviews> reviews = new ArrayList<Reviews>();
    private String mReviews;

    public ReviewFragment() {
        // Required empty public constructor
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (isConnected(getActivity())) {
            getLoaderManager().initLoader(DATA_RESULT_LOADER_REVIEWS_ID, savedInstanceState, dataResultLoaderReviews);
        } else {
            mainActivity.errorConnection();
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_film, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Intent i = getActivity().getIntent();
        movie = i.getExtras().getParcelable("PARAM_MOVIE");

        mReviews = String.format("%s%d/reviews?api_key=%s&language=pt-BR", mainActivity.URL_GENERIC, movie.getMovieId(), mainActivity.API_KEY);

        Bundle queryReviews = new Bundle();
        queryReviews.putString("url", mReviews);
        onActivityCreated(queryReviews);

        return rootView;
    }

    private LoaderManager.LoaderCallbacks<String> dataResultLoaderReviews = new LoaderManager.LoaderCallbacks<String>() {
        @Override
        public Loader<String> onCreateLoader(int id, final Bundle args) {
            return new AsyncTaskLoader<String>(getActivity()) {
                @Override
                protected void onStartLoading() {
                    super.onStartLoading();
                    if (args == null) {
                        Log.v("ARGS DETAIL: ", String.valueOf(args));
                        return;
                    }
                    forceLoad();
                }

                @Override
                public String loadInBackground() {
                    return mainActivity.download(mReviews);
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {
            reviews = JSON.parseReviews(data);
            adapter = new ReviewAdapter(getActivity(), reviews);
            mRecyclerView.setAdapter(adapter);

            Log.v("REVIEWS: ", String.valueOf(reviews.toArray().length));
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };

}
