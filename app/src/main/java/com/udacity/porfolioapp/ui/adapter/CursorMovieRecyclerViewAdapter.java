package com.udacity.porfolioapp.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.ListMoviesFragment;
import com.udacity.porfolioapp.model.MovieContract;
import com.udacity.porfolioapp.ui.holder.CursorMovieViewHolder;

/**
 * Created by jhurtace on 23/03/2017.
 */
public class CursorMovieRecyclerViewAdapter extends RecyclerView.Adapter<CursorMovieViewHolder> {

    // Class variables for the Cursor that holds task data and the Context
    private Cursor mCursor;
    private Context mContext;
    private ListMoviesFragment.Callbacks mCallbacks;
    private final static String baseUrlImage="http://image.tmdb.org/t/p/w300";
    /**
     * Constructor for the CustomCursorAdapter that initializes the Context.
     *
     * @param mContext the current Context
     * @param mCallbacks
     */
    public CursorMovieRecyclerViewAdapter(Context mContext, ListMoviesFragment.Callbacks mCallbacks) {
        this.mContext = mContext;
        this.mCallbacks=mCallbacks;
    }


    /**
     * Called when ViewHolders are created to fill a RecyclerView.
     *
     * @return A new TaskViewHolder that holds the view for each task
     */
    @Override
    public CursorMovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate the task_layout to a view
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_movie, null);
        layoutView.setTag(mCursor);
        return  new CursorMovieViewHolder(layoutView,mCallbacks);
    }


    /**
     * Called by the RecyclerView to display data at a specified position in the Cursor.
     *
     * @param holder The ViewHolder to bind Cursor data to
     * @param position The position of the data in the Cursor
     */
    @Override
    public void onBindViewHolder(CursorMovieViewHolder holder, int position) {

        // Indices for the _id, description, and priority columns
        int idIndex = mCursor.getColumnIndex(MovieContract.MovieEntry._ID);
        int titleIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE);
        int imageIndex = mCursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER);

        mCursor.moveToPosition(position); // get to the right location in the cursor

        // Determine the values of the wanted data
        final int id = mCursor.getInt(idIndex);
        String title = mCursor.getString(titleIndex);
        String imagePoster = mCursor.getString(imageIndex);

            //Set values
        //holder.itemView.setTag(id);
        holder.getTvNameMovie().setText(title);


        Glide.with(mContext).load(baseUrlImage+imagePoster).placeholder(R.drawable.placeholder).crossFade().into( holder.getIvImageMovie());

    }




    /**
     * Returns the number of items to display.
     */
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


}