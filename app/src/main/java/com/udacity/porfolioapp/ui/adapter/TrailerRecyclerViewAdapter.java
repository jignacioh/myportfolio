package com.udacity.porfolioapp.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.model.Movie;
import com.udacity.porfolioapp.ui.holder.MovieViewHolder;
import com.udacity.porfolioapp.ui.holder.TrailerViewHolder;

import java.util.List;

/**
 * Created by Juan PC
 */
public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerViewHolder> {

    private List<String> trailerList;
    private Context context;

    public TrailerRecyclerViewAdapter(Context context, List<String> trailerList) {
        this.trailerList = trailerList;
        this.context = context;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trailer, null);
        TrailerViewHolder rcv = new TrailerViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        //holder.tvTrailer.setText(movieList.get(position));
     }

    @Override
    public int getItemCount() {
        return this.trailerList.size();
    }
}