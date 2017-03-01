package com.udacity.porfolioapp.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.model.Review;
import com.udacity.porfolioapp.ui.holder.ReviewMovieHolder;

import java.util.List;

/**
 * Created by jhurtace on 01/03/2017.
 */
public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewMovieHolder> {

    private List<Review> itemList;
    //private OnChildClickListener onChildClickListener;

    public ReviewRecyclerViewAdapter( List<Review> itemList) {
        this.itemList = itemList;
    }

    @Override
    public ReviewMovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_app_item, null);
        ReviewMovieHolder rcv = new ReviewMovieHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ReviewMovieHolder holder, int position) {
        holder.tvDetail.setText(itemList.get(position).getContent());
        holder.tvAuthor.setText(itemList.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}