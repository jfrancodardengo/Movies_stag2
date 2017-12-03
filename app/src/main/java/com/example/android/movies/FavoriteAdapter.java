package com.example.android.movies;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.data.MoviesContract;
import com.squareup.picasso.Picasso;

/**
 * Created by CASA on 02/12/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>{

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;

    public FavoriteAdapter(Context mContext,Cursor mCursor) {
        this.mContext = mContext;
        this.mCursor = mCursor;
    }

    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflate the task_layout to a view
        View view = LayoutInflater.from(mContext).inflate(R.layout.model_film, parent, false);

        return new FavoriteViewHolder(view);
    }

    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {
        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(MoviesContract.MoviesEntry._ID);
        int idMovie = mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_ID_MOVIE);
        int nameIndex = mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE);
        int voteIndex = mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_VOTE_MOVIE);
        int imageIndex = mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_IMAGE_MOVIE);
        int synopsisIndex = mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_SYNOPSIS_MOVIE);
        int releaseIndex = mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_RELEASE_MOVIE);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        int movie = mCursor.getInt(idMovie);
        double mVoteAverage = mCursor.getDouble(voteIndex);
        String mOriginalTitle = mCursor.getString(nameIndex);
        String mImage = mCursor.getString(imageIndex);
        String mSynopsis = mCursor.getString(synopsisIndex);
        String mRealeaseDate = mCursor.getString(releaseIndex);

        //Set values
        holder.itemView.setTag(id);

        Picasso.with(mContext).load(mImage).into(holder.thumbnailFilm);

        holder.title.setText(mOriginalTitle);
        holder.vote.setText(String.valueOf(mVoteAverage));
        holder.release.setText(mRealeaseDate);
        holder.synopsis.setText(mSynopsis);

    }

    @Override
    public int getItemCount() {
        if(mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    public Cursor swapCursor(Cursor c) {
        // check if this cursor is the same as the previous cursor (mCursor)
        if (mCursor == c) {
            return null; // bc nothing has changed
        }
        Cursor temp = mCursor;
        this.mCursor = c; // new cursor value assigned

        //check if this is a valid cursor, then update the cursor
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }

    // Inner class for creating ViewHolders
    public class FavoriteViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnailFilm;
        TextView title, vote, release, synopsis;

        public FavoriteViewHolder(View itemView) {
            super(itemView);

            thumbnailFilm = (ImageView) itemView.findViewById(R.id.thumbnail_film);

            title = (TextView) itemView.findViewById(R.id.tv_title);
            vote = (TextView) itemView.findViewById(R.id.tv_vote);
            release = (TextView) itemView.findViewById(R.id.tv_release);
            synopsis = (TextView) itemView.findViewById(R.id.tv_synopsis);
        }
    }
}
