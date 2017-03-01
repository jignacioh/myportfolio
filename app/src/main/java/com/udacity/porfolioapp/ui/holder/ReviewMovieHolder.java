package com.udacity.porfolioapp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.udacity.porfolioapp.R;

/**
 * Created by jhurtace on 01/03/2017.
 */
public class ReviewMovieHolder extends RecyclerView.ViewHolder{

    public TextView tvAuthor,tvDetail;
    private View itemView;
    public ReviewMovieHolder(View itemView) {
        super(itemView);
        this.itemView=itemView;
        tvAuthor = (TextView) itemView.findViewById(R.id.tvDetailReview);
        tvDetail= (TextView) itemView.findViewById(R.id.tvAuthorReview);
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
}
