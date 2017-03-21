package com.udacity.porfolioapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.ListMoviesFragment;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.ui.holder.MovieViewHolder;

import java.util.List;

/**
 * Created by Juan PC
 */

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    private ListMoviesFragment.Callbacks mCallbacks;
    private Context context;
    private List<Movie> movieList;
    private final static String baseUrlImage="http://image.tmdb.org/t/p/w300";


    public MovieRecyclerViewAdapter(Context context, ListMoviesFragment.Callbacks mCallbacks, List<Movie> listMovies) {
        this.movieList = listMovies;
        this.context=context;
        this.mCallbacks = mCallbacks;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_movie, null);
        layoutView.setTag(movieList);
        return  new MovieViewHolder(layoutView,mCallbacks);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.getTvNameMovie().setText(movieList.get(position).getNameMovie());

        Glide.with(context).load(baseUrlImage+movieList.get(position).getUrlMovie()).placeholder(R.drawable.placeholder).crossFade().into( holder.getIvImageMovie());
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }
}