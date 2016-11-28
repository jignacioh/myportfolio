package com.udacity.porfolioapp.ui.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.udacity.porfolioapp.OnMovieClickListener;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.ListMoviesFragment;
import com.udacity.porfolioapp.model.Movie;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by clapj on 5/11/2016.
 */

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private View layoutView;
    private ListMoviesFragment.Callbacks mCallbacks;
    public TextView tvNameMovie;
    public ImageView ivImageMovie;

    public MovieViewHolder(View layoutView, Context context, ListMoviesFragment.Callbacks mCallbacks) {
        super(layoutView);
        this.layoutView=layoutView;
        this.mCallbacks=mCallbacks;
        itemView.setOnClickListener(this);
        tvNameMovie = (TextView) itemView.findViewById(R.id.tvNameMovie);
        ivImageMovie = (ImageView) itemView.findViewById(R.id.ivImageMovie);
    }

    @Override
    public void onClick(View view) {
        mCallbacks.onItemSelected((ArrayList<Movie>)layoutView.getTag(),getAdapterPosition());
        Toast.makeText(view.getContext(), tvNameMovie.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
