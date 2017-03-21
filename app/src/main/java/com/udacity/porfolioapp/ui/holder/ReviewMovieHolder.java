package com.udacity.porfolioapp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.ReviewsMovieFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jhurtace on 01/03/2017.
 */
public class ReviewMovieHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private final ReviewsMovieFragment.Callbacks mCallbacks;
    @BindView(R.id.tvAuthorReview)
    TextView tvAuthor;
    @BindView(R.id.tvDetailReview)
    TextView tvDetail;
    @BindView(R.id.tvReadMore)
    TextView tvReadMore;

    private View itemView;

    public ReviewMovieHolder(View itemView, ReviewsMovieFragment.Callbacks mCallbacks) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        this.itemView=itemView;
        this.mCallbacks=mCallbacks;
        tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthorReview);
        tvDetail= (TextView) itemView.findViewById(R.id.tvDetailReview );
        tvReadMore= (TextView) itemView.findViewById(R.id.tvReadMore);
        tvReadMore.setOnClickListener(this);
    }

    public TextView getTvAuthor() {
        return tvAuthor;
    }

    public void setTvAuthor(TextView tvAuthor) {
        this.tvAuthor = tvAuthor;
    }

    public TextView getTvDetail() {
        return tvDetail;
    }

    public void setTvDetail(TextView tvDetail) {
        this.tvDetail = tvDetail;
    }

    public View getItemView() {
        return itemView;
    }

    public void setItemView(View itemView) {
        this.itemView = itemView;
    }

    public TextView getTvReadMore() {
        return tvReadMore;
    }

    public void setTvReadMore(TextView tvReadMore) {
        this.tvReadMore = tvReadMore;
    }

    @Override
    public void onClick(View view) {
        mCallbacks.onReviewSelected((ArrayList<Object>) itemView.getTag(),getAdapterPosition(),itemView);
        //Toast.makeText(view.getContext(), tvTrailer.getText().toString(), Toast.LENGTH_SHORT).show();
    }
}
