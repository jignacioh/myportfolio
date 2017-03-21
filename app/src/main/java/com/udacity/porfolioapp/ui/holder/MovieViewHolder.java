package com.udacity.porfolioapp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.ListMoviesFragment;
import com.udacity.porfolioapp.model.Movie;

import java.util.ArrayList;

/**
 * Created by clapj on 5/11/2016.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private View layoutView;
    private ListMoviesFragment.Callbacks mCallbacks;
    public TextView tvNameMovie;
    public ImageView ivImageMovie;

    public MovieViewHolder(View layoutView, ListMoviesFragment.Callbacks mCallbacks) {
        super(layoutView);
        this.layoutView=layoutView;
        this.mCallbacks=mCallbacks;
        itemView.setOnClickListener(this);
        tvNameMovie = (TextView) itemView.findViewById(R.id.tvNameMovie);
        ivImageMovie = (ImageView) itemView.findViewById(R.id.ivImageMovie);
    }

    @Override
    public void onClick(View view) {
        mCallbacks.onItemSelected((ArrayList<Movie>)layoutView.getTag(),getAdapterPosition(), layoutView.findViewById(R.id.ivImageMovie));
    }
}
