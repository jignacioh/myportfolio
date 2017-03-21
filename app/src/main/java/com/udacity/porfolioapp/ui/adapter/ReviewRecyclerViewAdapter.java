package com.udacity.porfolioapp.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.ReviewsMovieFragment;
import com.udacity.porfolioapp.model.Review;
import com.udacity.porfolioapp.ui.holder.ReviewMovieHolder;

import java.util.List;

/**
 * Created by jhurtace on 01/03/2017.
 */
public class ReviewRecyclerViewAdapter extends RecyclerView.Adapter<ReviewMovieHolder> {

    private Context context;
    private ReviewsMovieFragment.Callbacks mCallbacks ;
    private List<Review> itemList;


    public ReviewRecyclerViewAdapter(List<Review> listReviews, ReviewsMovieFragment.Callbacks mCallbacks, Context context) {
        this.itemList = listReviews;
        this.mCallbacks=mCallbacks;
        this.context=context;
    }


    @Override
    public ReviewMovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_review, null);
        layoutView.setTag(itemList);
        ReviewMovieHolder rcv = new ReviewMovieHolder(layoutView,mCallbacks);
        return rcv;
    }

    @Override
    public void onBindViewHolder(ReviewMovieHolder holder, int position) {
        holder.getTvDetail().setText(itemList.get(position).getContent());
        holder.getTvAuthor().setText(itemList.get(position).getAuthor());


        String styledText = context.getString(R.string.lb_read_more);
        holder.getTvReadMore().setText(styledText);
        holder.getTvReadMore().setPaintFlags( holder.getTvReadMore().getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.getTvReadMore().setClickable(true);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}