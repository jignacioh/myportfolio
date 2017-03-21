package com.udacity.porfolioapp.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.udacity.porfolioapp.R;
import com.udacity.porfolioapp.fragment.DetailsMovieFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jhurtace on 31/01/2017.
 */
public class DetailMovieHolder extends RecyclerView.ViewHolder implements CompoundButton.OnCheckedChangeListener{

    private DetailsMovieFragment.Callbacks mCallbacks;
    @BindView(R.id.ivAddFavo)
    ToggleButton ivAddFavo;
    @BindView(R.id.ivDetailMovie)
    ImageView ivPoster;
    @BindView(R.id.tvPopularityMovie)
    TextView tvPopularityMovie;
    @BindView(R.id.tvSummaryMovie)
    TextView tvSummaryMovie;
    @BindView(R.id.tvVotosMovie)
    TextView tvVotosMovie;
    @BindView(R.id.tvRatedMovie)
    TextView tvRatedMovie;
    @BindView(R.id.tvYearMovie)
    TextView tvYearMovie;

    public DetailMovieHolder(View view, DetailsMovieFragment.Callbacks mCallbacks){
        super(view);
        ButterKnife.bind(this, view);
        this.mCallbacks=mCallbacks;
        ivAddFavo.setOnCheckedChangeListener(this);
    }
    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean check) {
        mCallbacks.onItemCheckFavorite(check);
    }

    public ToggleButton getIvAddFavo() {
        return ivAddFavo;
    }

    public void setIvAddFavo(ToggleButton ivAddFavo) {
        this.ivAddFavo = ivAddFavo;
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
