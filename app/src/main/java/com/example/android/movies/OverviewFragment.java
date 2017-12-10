package com.example.android.movies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class OverviewFragment extends Fragment {
    Movie movie = new Movie();
    RecyclerView mRecyclerView;
    OverviewAdapter adapter;

    public OverviewFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.activity_film, container, false);

        //get intent movie
        Intent i = getActivity().getIntent();
        movie = i.getExtras().getParcelable("movie");

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new OverviewAdapter(getActivity(),movie);
        mRecyclerView.setAdapter(adapter);

        return rootView;
    }

}
