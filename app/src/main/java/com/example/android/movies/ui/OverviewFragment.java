package com.example.android.movies.ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.movies.R;
import com.example.android.movies.adapters.OverviewAdapter;
import com.example.android.movies.model.Movie;


/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {

    public OverviewFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_film, container, false);

        //get intent movie
        Intent i = getActivity().getIntent();
        Movie movie = i.getExtras().getParcelable("PARAM_MOVIE");

        RecyclerView mRecyclerView = rootView.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        OverviewAdapter adapter = new OverviewAdapter(getActivity(), movie);
        mRecyclerView.setAdapter(adapter);

        return rootView;
    }

}
