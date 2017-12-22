package com.example.android.movies.ui;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.movies.R;
import com.example.android.movies.adapters.TrailerAdapter;
import com.example.android.movies.data.JSON;
import com.example.android.movies.model.Movie;
import com.example.android.movies.model.Videos;

import java.util.List;
import java.util.Locale;

import static com.example.android.movies.utils.Utils.isConnected;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrailerFragment extends Fragment {
    private static final int DATA_RESULT_LOADER_VIDEOS_ID = 1;

    private Communication listener;
    private final MainActivity mainActivity = new MainActivity();
    private RecyclerView mRecyclerView;
    private String mVideos;

    public TrailerFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (isConnected(getActivity())) {
            getLoaderManager().initLoader(DATA_RESULT_LOADER_VIDEOS_ID, savedInstanceState, dataResultLoaderVideos);
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
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        Intent i = getActivity().getIntent();
        Movie movie = i.getExtras().getParcelable("PARAM_MOVIE");
        mVideos = String.format(Locale.getDefault(), "%s%d/videos?api_key=%s&language=pt-BR", MainActivity.URL_GENERIC, movie.getMovieId(), MainActivity.API_KEY);

        Bundle queryVideos = new Bundle();
        queryVideos.putString("url", mVideos);
        onActivityCreated(queryVideos);

        return rootView;
    }

    private final LoaderManager.LoaderCallbacks<String> dataResultLoaderVideos = new LoaderManager.LoaderCallbacks<String>() {
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
                    return mainActivity.download(mVideos);
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<String> loader, String data) {
            List<Videos> videos = JSON.parseVideos(data);
            listener.onArticleSelected(videos);
            TrailerAdapter adapter = new TrailerAdapter(getActivity(), videos);
            mRecyclerView.setAdapter(adapter);
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };

    public interface Communication {
        void onArticleSelected(List<Videos> position);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (Communication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(String.format("%s must implement OnHeadlineSelectedListener", context.toString()));
        }
    }

}
