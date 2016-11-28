package com.udacity.porfolioapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.udacity.porfolioapp.OnMovieClickListener;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.ListMoviesFragment;
import com.udacity.porfolioapp.model.AppObject;
import com.udacity.porfolioapp.model.ListMovie;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.ui.holder.MovieViewHolder;
import com.udacity.porfolioapp.ui.holder.RecyclerViewHolders;

import java.util.List;

/**
 * Created by clapj on 5/11/2016.
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private ListMoviesFragment.Callbacks mCallbacks;
    private Context context;
    private List<Movie> movieList;
    private Context listMoviesFragment;

    public MovieRecyclerViewAdapter(Context listMoviesFragment, List<Movie> movieList) {
        this.movieList = movieList;
        this.listMoviesFragment = listMoviesFragment;
    }

    public MovieRecyclerViewAdapter(Context context, ListMoviesFragment.Callbacks mCallbacks, List<Movie> listMovies) {
        this.movieList = listMovies;
        this.context=context;
        this.mCallbacks = mCallbacks;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_movie, null);
        layoutView.setTag(movieList);
        MovieViewHolder rcv = new MovieViewHolder(layoutView,context,mCallbacks);
        return rcv;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.tvNameMovie.setText(movieList.get(position).getNameMovie());

        Glide.with(context).load(movieList.get(position).getUrlMovie()).placeholder(R.drawable.placeholder).crossFade().into( holder.ivImageMovie);
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }
}