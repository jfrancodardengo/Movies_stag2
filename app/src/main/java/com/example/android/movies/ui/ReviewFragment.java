package com.example.android.movies.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.example.android.movies.R;
import com.example.android.movies.adapters.ReviewAdapter;
import com.example.android.movies.data.JSON;
import com.example.android.movies.model.Movie;
import com.example.android.movies.model.Reviews;

import java.util.List;
import java.util.Locale;

import static com.example.android.movies.utils.Utils.isConnected;


/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewFragment extends Fragment {
    private static final int DATA_RESULT_LOADER_REVIEWS_ID = 2;
    private final MainActivity mainActivity = new MainActivity();

    private RecyclerView mRecyclerView;
    private String mReviews;

    public ReviewFragment() {
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_film, container, false);

        mRecyclerView = rootView.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Intent i = getActivity().getIntent();
        Movie movie = i.getExtras().getParcelable("PARAM_MOVIE");

        mReviews = String.format(Locale.getDefault(), "%s%d/reviews?api_key=%s&language=pt-BR", MainActivity.URL_GENERIC, movie.getMovieId(), MainActivity.API_KEY);

        Bundle queryReviews = new Bundle();
        queryReviews.putString("url", mReviews);
        onActivityCreated(queryReviews);

        return rootView;
    }

    private final LoaderManager.LoaderCallbacks<String> dataResultLoaderReviews = new LoaderManager.LoaderCallbacks<String>() {
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
            List<Reviews> reviews = JSON.parseReviews(data);
            ReviewAdapter adapter = new ReviewAdapter(getActivity(), reviews);
            mRecyclerView.setAdapter(adapter);
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };

}
