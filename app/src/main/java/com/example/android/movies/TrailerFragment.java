package com.example.android.movies;


import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrailerFragment extends Fragment {
    MainActivity mainActivity = new MainActivity();
    private Movie movie;

    private static final int DATA_RESULT_LOADER_VIDEOS_ID = 1 ;

    RecyclerView mRecyclerView;

    TrailerAdapter adapter;

    private List<Videos> videos = new ArrayList<Videos>();

    private String mVideos;


    public TrailerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(DATA_RESULT_LOADER_VIDEOS_ID, savedInstanceState, dataResultLoaderVideos);
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.activity_film, container, false);

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        Intent i = getActivity().getIntent();
        movie = i.getExtras().getParcelable("movie");

        mVideos = mainActivity.URL_GENERIC + movie.getMovieId() + "/videos?api_key=" + mainActivity.apiKey + "&language=pt-BR";

        Bundle queryVideos = new Bundle();
        queryVideos.putString("url", mVideos);
        onActivityCreated(queryVideos);

//        getSupportLoaderManager().initLoader(DATA_RESULT_LOADER_VIDEOS_ID, queryVideos, dataResultLoaderVideos);


        return rootView;
    }

    private LoaderManager.LoaderCallbacks<String> dataResultLoaderVideos = new LoaderManager.LoaderCallbacks<String>() {
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

                    if (mainActivity.isConnected(getActivity())) {
                        Log.v("INTERNET: ", "CONNECTED");
                    } else {
                        Log.v("INTERNET: ", "DISCONNECTED");
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
            if (data.startsWith("Error")) {
                String error = data;
                Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
            } else {
                videos = JSON.parseVideos(data);
                adapter = new TrailerAdapter(getActivity(),videos);
//                adapter.addTrailers(videos);

                mRecyclerView.setAdapter(adapter);

                Log.v("VIDEOS: ", String.valueOf(videos.toArray().length));

            }
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };

}
