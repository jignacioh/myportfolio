package com.udacity.porfolioapp.ui.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    //private OnChildClickListener onChildClickListener;


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
        holder.tvDetail.setText(itemList.get(position).getContent());
        holder.tvAuthor.setText(itemList.get(position).getAuthor());


        String styledText = context.getString(R.string.lb_read_more);
        holder.tvReadMore.setText(styledText);
        holder.tvReadMore.setPaintFlags( holder.tvReadMore.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        holder.tvReadMore.setClickable(true);
    }

    @Override
    public int getItemCount() {
        return this.itemList.size();
    }
}