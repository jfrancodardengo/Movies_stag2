package com.example.android.movies;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movies.data.MoviesContract;
import com.squareup.picasso.Picasso;

/**
 * Created by CASA on 02/12/2017.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;

    Movie movie;

    public FavoriteAdapter(Context mContext, Cursor mCursor) {
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
     * @param holder   The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(final FavoriteViewHolder holder, int position) {

        movie = populateMovie(mCursor, position, holder);

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(mContext, DetailActivity.class);
                i.putExtra("movie", movie);
                mContext.startActivity(i);
            }
        });

    }

    Movie populateMovie(Cursor cursor, int position, FavoriteViewHolder holder) {
        try {
            cursor.moveToPosition(position);
            int idMovie = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_ID_MOVIE);
            int titleIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_NAME_MOVIE);
            int voteIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_VOTE_MOVIE);
            int imageIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_IMAGE_MOVIE);
            int synopsisIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_SYNOPSIS_MOVIE);
            int releaseIndex = cursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_RELEASE_MOVIE);


            // Determine the values of the wanted data
            int id = cursor.getInt(idMovie);
            String mTitle = cursor.getString(titleIndex);
            double mVote = cursor.getDouble(voteIndex);
            String mImage = cursor.getString(imageIndex);
            String mSynopsis = cursor.getString(synopsisIndex);
            String mRelease = cursor.getString(releaseIndex);

            Picasso.with(mContext).load(mImage).into(holder.thumbnailFilm);

            return new Movie(id, mVote, mTitle, mImage, mSynopsis, mRelease);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    /**
     * When data changes and a re-query occurs, this function swaps the old Cursor
     * with a newly updated Cursor (Cursor c) that is passed in.
     */
    void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    // Inner class for creating ViewHolders
    public class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnCreateContextMenuListener {
        ImageView thumbnailFilm;
        TextView title, vote, release, synopsis;

        ItemClickListener itemClickListener;

        public FavoriteViewHolder(View itemView) {
            super(itemView);

            thumbnailFilm = (ImageView) itemView.findViewById(R.id.thumbnail_film);

            title = (TextView) itemView.findViewById(R.id.tv_title);
            vote = (TextView) itemView.findViewById(R.id.tv_vote);
            release = (TextView) itemView.findViewById(R.id.tv_release);
            synopsis = (TextView) itemView.findViewById(R.id.tv_synopsis);

            itemView.setOnClickListener(this);

            //Chamada para criar contexto de menu
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(this.getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }


        //CREATED MENU CONTEXT
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Delete = menu.add(Menu.NONE, 1, Menu.NONE, "Excluir");
            Delete.setOnMenuItemClickListener(onEditMenu);
        }


        //CREATED MENU ITEM
        private final MenuItem.OnMenuItemClickListener onEditMenu = new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(final MenuItem item) {
                switch (item.getItemId()) {
                    case 1:
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setMessage("Confirma a exclusão?");
                        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                int id = item.getItemId();
                                int idMovie = movie.getMovieId();
                                String stringId = Integer.toString(idMovie);
                                Uri uri = MoviesContract.MoviesEntry.CONTENT_URI;
                                uri = uri.buildUpon().appendPath(stringId).build();

                                DatabaseUtils.dumpCursor(mCursor);

                                mContext.getContentResolver().delete(uri, MoviesContract.MoviesEntry.COLUMN_ID_MOVIE, new String[]{String.valueOf(idMovie)});

                                mContext.getContentResolver().notifyChange(uri,null);



                            }
                        });

                        builder.setNegativeButton("Não", null);
                        AlertDialog dialog = builder.create();
                        dialog.setTitle("Confirmar operação");
                        dialog.show();

                        break;
                }
                return true;
            }
        };
    }
}

