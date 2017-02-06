package com.udacity.porfolioapp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.DetailMovieFragment;

/**
 * Created by jhurtace on 31/01/2017.
 */
public class DetailMovieHolder extends RecyclerView.ViewHolder {

    private TextView tvTittle;
    private ImageView ivPoster;
    private TextView tvPopularityMovie;
    private TextView tvSummaryMovie;
    private TextView tvVotosMovie;
    private TextView tvRatedMovie;
    private TextView tvYearMovie;

    public DetailMovieHolder(View view, DetailMovieFragment.Callbacks mCallbacks){
        super(view);
        //tvTittle=(TextView)view.findViewById(R.id.tvLink);
        ivPoster=(ImageView) view.findViewById(R.id.ivDetailMovie);
        tvPopularityMovie=(TextView) view.findViewById(R.id.tvPopularityMovie);
        tvSummaryMovie=(TextView) view.findViewById(R.id.tvSummaryMovie);
        tvVotosMovie=(TextView) view.findViewById(R.id.tvVotosMovie);
        tvRatedMovie=(TextView) view.findViewById(R.id.tvRatedMovie);
        tvYearMovie=(TextView) view.findViewById(R.id.tvYearMovie);

    }

    public TextView getTvTittle() {
        return tvTittle;
    }

    public void setTvTittle(TextView tvTittle) {
        this.tvTittle = tvTittle;
    }

    public ImageView getIvPoster() {
        return ivPoster;
    }

    public void setIvPoster(ImageView ivPoster) {
        this.ivPoster = ivPoster;
    }

    public TextView getTvPopularityMovie() {
        return tvPopularityMovie;
    }

    public void setTvPopularityMovie(TextView tvPopularityMovie) {
        this.tvPopularityMovie = tvPopularityMovie;
    }

    public TextView getTvSummaryMovie() {
        return tvSummaryMovie;
    }

    public void setTvSummaryMovie(TextView tvSummaryMovie) {
        this.tvSummaryMovie = tvSummaryMovie;
    }

    public TextView getTvVotosMovie() {
        return tvVotosMovie;
    }

    public void setTvVotosMovie(TextView tvVotosMovie) {
        this.tvVotosMovie = tvVotosMovie;
    }

    public TextView getTvRatedMovie() {
        return tvRatedMovie;
    }

    public void setTvRatedMovie(TextView tvRatedMovie) {
        this.tvRatedMovie = tvRatedMovie;
    }

    public TextView getTvYearMovie() {
        return tvYearMovie;
    }

    public void setTvYearMovie(TextView tvYearMovie) {
        this.tvYearMovie = tvYearMovie;
    }
}
