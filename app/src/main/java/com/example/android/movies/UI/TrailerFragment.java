package com.example.android.movies.UI;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.movies.MainActivity;
import com.example.android.movies.R;
import com.example.android.movies.adapters.TrailerAdapter;
import com.example.android.movies.data.JSON;
import com.example.android.movies.data.Movie;
import com.example.android.movies.data.Videos;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrailerFragment extends Fragment {

    Communication listener;

    public interface Communication{
        public void onArticleSelected(List<Videos> position);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            listener = (Communication) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHeadlineSelectedListener");
        }
    }


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
        mRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        Intent i = getActivity().getIntent();
        movie = i.getExtras().getParcelable("movie");

        mVideos = mainActivity.URL_GENERIC + movie.getMovieId() + "/videos?api_key=" + mainActivity.apiKey + "&language=pt-BR";

        Bundle queryVideos = new Bundle();
        queryVideos.putString("url", mVideos);
        onActivityCreated(queryVideos);

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
                listener.onArticleSelected(videos);
                adapter = new TrailerAdapter(getActivity(),videos);

                mRecyclerView.setAdapter(adapter);

                Log.v("VIDEOS: ", String.valueOf(videos.toArray().length));

            }
        }

        @Override
        public void onLoaderReset(Loader<String> loader) {

        }
    };

    public Intent createShareVideoIntent() {
        String urlVideo = "https://www.youtube.com/watch?v=" + videos.get(0).getKey();
        Intent shareIntent = ShareCompat.IntentBuilder.from(getActivity())
                .setType("text/plain")
                .setChooserTitle(videos.get(0).getName())
                .setText(urlVideo)
                .getIntent();


        if (shareIntent.resolveActivity(getActivity().getPackageManager()) != null){
            startActivity(shareIntent);
        }
        return shareIntent;
    }

}
